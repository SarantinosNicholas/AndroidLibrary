package library.mgiandia.com.androidlibrary.view.Borrower.AddEditBorrower;

import android.util.Patterns;

import java.util.ArrayList;
import java.util.List;

import library.mgiandia.com.androidlibrary.contacts.Address;
import library.mgiandia.com.androidlibrary.contacts.EmailAddress;
import library.mgiandia.com.androidlibrary.contacts.TelephoneNumber;
import library.mgiandia.com.androidlibrary.contacts.ZipCode;
import library.mgiandia.com.androidlibrary.dao.BorrowerCategoryDAO;
import library.mgiandia.com.androidlibrary.dao.BorrowerDAO;
import library.mgiandia.com.androidlibrary.domain.Borrower;
import library.mgiandia.com.androidlibrary.domain.BorrowerCategory;

/**
 * @author Νίκος Σαραντινός
 *
 * Υλοποιήθηκε στα πλαίσια του μαθήματος Τεχνολογία Λογισμικού το έτος 2016-2017 υπό την επίβλεψη του Δρ. Βασίλη Ζαφείρη.
 *
 */

public class AddEditBorrowerPresenter {

    private AddEditBorrowerView view;

    private BorrowerDAO borrowers;
    private BorrowerCategoryDAO borrowerCategories;
    private List<String> countries;

    private Borrower attachedBorrower;

    private boolean verifyOnlyDigits(String in)
    {
        for(int i = 0; i < in.length(); i++)
            if(!Character.isDigit(in.charAt(i)))
                return false;

        return true;
    }

    public AddEditBorrowerPresenter(AddEditBorrowerView view, BorrowerDAO borrowers, BorrowerCategoryDAO borrowerCategories, List<String> countries)
    {
        this.view = view;
        this.borrowers = borrowers;
        this.borrowerCategories = borrowerCategories;
        this.countries = countries;

        view.setCountryList(countries);

        List<String> borrowerCategoryNames = new ArrayList<>();
        for(BorrowerCategory borrowerCategory : borrowerCategories.findAll()) borrowerCategoryNames.add(borrowerCategory.getDescription());
        view.setCategoryList(borrowerCategoryNames);

        Integer attachedBorrowerID = view.getAttachedBorrowerID();
        attachedBorrower = attachedBorrowerID == null ? null : borrowers.find(attachedBorrowerID);

        if(attachedBorrower != null)//edit mode
        {
            view.setPageName("Δανειζόμενος #" + attachedBorrower.getBorrowerNo());

            view.setFirstName(attachedBorrower.getFirstName());
            view.setLastName(attachedBorrower.getLastName());
            view.setCategoryPosition(attachedBorrower.getCategory().getId());
            view.setPhone(attachedBorrower.getTelephone().getTelephoneNumber());
            view.setEmail(attachedBorrower.getEmail().getAddress());
            view.setCountryPosition(countries.indexOf(attachedBorrower.getAddress().getCountry()));
            view.setAddressCity(attachedBorrower.getAddress().getCity());
            view.setAddressStreet(attachedBorrower.getAddress().getStreet());
            view.setAddressNumber(attachedBorrower.getAddress().getNumber());
            view.setAddressPostalCode(attachedBorrower.getAddress().getZipCode().getCode());
        }
    }

    public void onSaveBorrower()
    {
        String
                firstName = view.getFirstName(),
                lastName = view.getLastName(),
                phone = view.getPhone(),
                email = view.getEmail(),
                addressCity = view.getAddressCity(),
                addressStreet = view.getAddressStreet(),
                addressNumber = view.getAddressNumber(),
                addressPostalCode = view.getAddressPostalCode();

        Integer
                categoryPosition = view.getCategoryPosition(),
                countryPosition = view.getCountryPosition();

        if(firstName.length() < 2 || firstName.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στο Όνομα.");
        else if(lastName.length() < 2 || lastName.length() > 15)
            view.showErrorMessage("Σφάλμα!", "Συμπληρώστε 2 έως 15 χαρακτήρες στο Επώνυμο.");
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

            if(attachedBorrower == null)//add
            {
                borrowers.save(new Borrower(borrowers.nextId(), firstName, lastName, addr, new EmailAddress(email), new TelephoneNumber(phone), borrowerCategories.find(categoryPosition)));
                view.successfullyFinishActivity("Επιτυχής Εγγραφή του '"+lastName+" "+firstName+"'!");

            }
            else//update
            {
                attachedBorrower.setFirstName(firstName);
                attachedBorrower.setLastName(lastName);
                attachedBorrower.setAddress(addr);
                attachedBorrower.setEmail(new EmailAddress(email));
                attachedBorrower.setTelephone(new TelephoneNumber(phone));
                attachedBorrower.setCategory(borrowerCategories.find(categoryPosition));

                view.successfullyFinishActivity("Επιτυχής Τροποποίηση του '"+lastName+" "+firstName+"'!");
            }
        }
    }
}
