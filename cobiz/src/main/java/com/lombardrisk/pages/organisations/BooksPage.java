package com.lombardrisk.pages.organisations;

import com.lombardrisk.pojo.OrganisationBook;
import com.lombardrisk.pojo.SimpleStatusType;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

/**
 * @author Kenny Wang
 */
public final class BooksPage extends PageBase {

    public BooksPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * add book items in edit book hierarchy page
     *
     * @param book
     */
    public void addBooks(OrganisationBook book) throws Exception {
        if (book.getParent() == null) {
            element("og.book.add").click();
        } else {
            if(!element("og.book", book.getParent().getOrganisationBookName().getRealValue()).isDisplayed()){
                OrganisationBook organisationBook = new OrganisationBook();
                organisationBook.setOrganisationBookName(book.getParent().getOrganisationBookName());
                organisationBook.setParent(book.getParent().getParent());
                organisationBook.setStatus(SimpleStatusType.APPROVED);
                addBooks(organisationBook);
            }
            expandParent(book);
        }

        if (book.getOrganisationBookName() != null)
            element("og.book.name").input(book.getOrganisationBookName().getRealValue());
        if (book.getStatus() != null)
            element("og.book.status").selectByVisibleText(book.getStatus().value());
        element("og.book.save").click();
    }

    private void expandParent(OrganisationBook book) throws Exception{
        if(element("og.book.expand", book.getParent().getOrganisationBookName().getRealValue()).isDisplayed())
            element("og.book.expand", book.getParent().getOrganisationBookName().getRealValue()).click();
        if(book.getParent().getParent() != null){
            OrganisationBook organisationBook = new OrganisationBook();
            organisationBook.setParent(book.getParent().getParent());
            expandParent(organisationBook);
        }else {
            element("og.book.add.child", book.getParent().getOrganisationBookName().getRealValue()).click();
        }
    }

    
    public void deleteBook(){}

    public void assertOrganisationBook(OrganisationBook book) throws Exception{
        if (book.getParent()==null) {
            assertThat("og.book.name.a", book.getOrganisationBookName().getRealValue()).displayed().isTrue();
        }else {
            if (book.getParent() != null && book.getParent().getOrganisationBookName() != null) {
                OrganisationBook organisationBook = new OrganisationBook();
                organisationBook.setOrganisationBookName(book.getParent().getOrganisationBookName());
                organisationBook.setParent(book.getParent().getParent());
                assertOrganisationBook(organisationBook);
                if(element("og.book.expand", organisationBook.getOrganisationBookName().getRealValue()).isDisplayed()){
                    element("og.book.expand", organisationBook.getOrganisationBookName().getRealValue()).click();
                    assertThat("og.book.name.a", book.getOrganisationBookName().getRealValue()).displayed().isTrue();
                }
            }
        }
    }
}
