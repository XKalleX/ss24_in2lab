package com.haw.srs.customerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MailGateway mailGateway;

    @Transactional
    public void enrollInCourse(String lastName, Course course) throws CustomerNotFoundException {
        Customer customer = customerRepository
                .findByLastName(lastName)
                .orElseThrow(() -> new CustomerNotFoundException(lastName));

        // Überprüfe, ob der Kunde bereits im Kurs eingeschrieben ist
        if (!customer.getCourses().contains(course)) {
            // Füge den Kurs dem Kunden hinzu
            customer.addCourse(course);

            // Erhöhe die Anzahl der Teilnehmer des Kurses um 1
            course.setAnzahlTeilnehmer(course.getAnzahlTeilnehmer() + 1);

            // Speichere den Kunden
            customerRepository.save(customer);
        }
    }

    @Transactional
    public void transferCourses(String fromCustomerLastName, String toCustomerLastName) throws CustomerNotFoundException {
        Customer from = customerRepository
                .findByLastName(fromCustomerLastName)
                .orElseThrow(() -> new CustomerNotFoundException(fromCustomerLastName));
        Customer to = customerRepository
                .findByLastName(toCustomerLastName)
                .orElseThrow(() -> new CustomerNotFoundException(toCustomerLastName));

        // Übertrage die Kurse nur, wenn der Kunde Kurse hat
        if (!from.getCourses().isEmpty()) {
            // Füge die Kurse dem Zielkunden hinzu
            to.getCourses().addAll(from.getCourses());

            // Keine Änderungen am Attribut 'anzahlTeilnehmer' hier, da die Teilnehmeranzahl beim Transfer gleich bleibt.

            // Lösche die Kurse des Ursprungskunden
            from.getCourses().clear();

            // Speichere die Änderungen
            customerRepository.save(from);
            customerRepository.save(to);
        }
    }

    @Transactional
    public void cancelMembership(CustomerNumber customerNumber, CourseNumber courseNumber) throws CustomerNotFoundException, CourseNotFoundException, MembershipMailNotSent {
        // some implementation goes here
        // find customer, find course, look for membership, remove membership, etc.
        String customerMail = "customer@domain.com";

        // Stelle sicher, dass der Kunde Mitglied des Kurses ist, bevor die Mitgliedschaft storniert wird

        boolean mailWasSent = mailGateway.sendMail(customerMail, "Oh, we're sorry that you canceled your membership!", "Some text to make her/him come back again...");
        if (!mailWasSent) {
            // do some error handling here (including e.g. transaction rollback, etc.)
            // ...
            throw new MembershipMailNotSent(customerMail);
        }
    }
}
