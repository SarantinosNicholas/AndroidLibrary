package library.mgiandia.com.androidlibrary.view.Publisher.AddPublisher;

import android.util.Patterns;

import java.util.List;

import library.mgiandia.com.androidlibrary.contacts.Address;
import library.mgiandia.com.androidlibrary.contacts.EmailAddress;
import library.mgiandia.com.androidlibrary.contacts.TelephoneNumber;
import library.mgiandia.com.androidlibrary.contacts.ZipCode;
import library.mgiandia.com.androidlibrary.dao.PublisherDAO;
import library.mgiandia.com.androidlibrary.domain.Publisher;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AddEditPublisherPresenter
{
    private AddEditPublisherView view;

    private PublisherDAO publishers;
    private List<String> countries;

    private Publisher attachedPublisher;

    private boolean verifyOnlyDigits(String in)
    {
        for(int i = 0; i < in.length(); i++)
            if(!Character.isDigit(in.charAt(i)))
                return false;

        return true;
    }

    public AddEditPublisherPresenter(AddEditPublisherView view, PublisherDAO publishers, List<String> countries)
    {
        this.view = view;
        this.publishers = publishers;
        this.countries = countries;

        view.setCountryList(countries);

        Integer attachedPublisherID = view.getAttachedPublisherID();
        attachedPublisher = attachedPublisherID == null ? null : publishers.find(attachedPublisherID);

        if(attachedPublisher != null)//edit mode
        {
            view.setPageName("Εκδοτικός Οίκος #" + attachedPublisher.getId());

            view.setName(attachedPublisher.getName());
            view.setPhone(attachedPublisher.getTelephone().getTelephoneNumber());
            view.setEmail(attachedPublisher.getEMail().getAddress());
            view.setCountryPosition(countries.indexOf(attachedPublisher.getAddress().getCountry()));
            view.setAddressCity(attachedPublisher.getAddress().getCity());
            view.setAddressStreet(attachedPublisher.getAddress().getStreet());
            view.setAddressNumber(attachedPublisher.getAddress().getNumber());
            view.setAddressPostalCode(attachedPublisher.getAddress().getZipCode().getCode());
        }
    }

    public void onSavePublisher()
    {
        String
                name = view.getName(),
                phone = view.getPhone(),
                email = view.getEmail(),
                addressCity = view.getAddressCity(),
                addressStreet = view.getAddressStreet(),
                addressNumber = view.getAddressNumber(),
                addressPostalCode = view.getAddressPostalCode();

        Integer countryPosition = view.getCountryPosition();

        if(name.length() < 2 || name.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στο Όνομα.");
        else if(phone.length() < 2 || phone.length() > 15 || !Patterns.PHONE.matcher(phone).matches())
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε ορθά το Τηλέφωνο.");
        else if(email.length() < 2 || email.length() > 100 || !Patterns.EMAIL_ADDRESS.matcher(email).matches())
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε ορθά το Email.");
        else if(addressCity.length() < 2 || addressCity.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στην Πόλη.");
        else if(addressStreet.length() < 2 || addressStreet.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στην Οδό.");
        else if(addressNumber.length() < 2 || addressNumber.length() > 10 || !verifyOnlyDigits(addressNumber))
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 10 αριθμητικά ψηφία στον Αριθμό.");
        else if(addressPostalCode.length() < 2 || addressPostalCode.length() > 10 || !verifyOnlyDigits(addressPostalCode))
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 10 αριθμητικά ψηφία στον Τ.Κ.");
        else
        {
            Address addr = new Address(addressStreet, addressNumber, addressCity, new ZipCode(addressPostalCode), countries.get(countryPosition));

            if(attachedPublisher == null)//add
            {
                publishers.save(new Publisher(publishers.nextId(), name, addr, new EmailAddress(email), new TelephoneNumber(phone)));
                view.successfullyFinishActivity("Επιτυχής Εγγραφή του '"+name+"'!");

            }
            else//update
            {
                attachedPublisher.setName(name);
                attachedPublisher.setAddress(addr);
                attachedPublisher.setEMail(new EmailAddress(email));
                attachedPublisher.setTelephone(new TelephoneNumber(phone));

                view.successfullyFinishActivity("Επιτυχής Τροποποίηση του '"+name+"'!");
            }
        }
    }
}
