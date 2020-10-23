package emp.project.librarymanagementapp.Models;

public class FAQModel {
    String faq_id,faq_reply,faq_question;

    public FAQModel() {
    }

    public FAQModel(String faq_id, String faq_reply, String faq_question) {
        this.faq_id = faq_id;
        this.faq_reply = faq_reply;
        this.faq_question = faq_question;
    }

    public String getFaq_id() {
        return faq_id;
    }

    public String getFaq_reply() {
        return faq_reply;
    }

    public String getFaq_question() {
        return faq_question;
    }
}
