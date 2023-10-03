package ru.podgoretskaya.accounting.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.podgoretskaya.accounting.dto.EmployeeCardDTO;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCardServiceImp implements EmployeeCardService {
    @Value("${ageMax}")
    private int ageMax;
    @Value("${phoneNumberLength}")
    private int phoneNumberLength;

    @Override
    public EmployeeCardDTO save(EmployeeCardDTO model) {
        firstLastNameCard(model);
        birthdateCard(model);
        emailCard(model);
        phoneNumberCard(model);
        return model;
    }

    private void firstLastNameCard(EmployeeCardDTO model) {
        Pattern patlatletter = Pattern.compile("^[a-zA-Z]{2,30}$");
        log.debug("имя " + model.getFirstName());
        Matcher firstNameLatLetter = patlatletter.matcher(model.getFirstName());
        if (!firstNameLatLetter.matches()) {
            log.info("проверьте имя" + model.getFirstName());
            throw new IllegalArgumentException("проверьте ФИО");
        }
        log.debug("фамилия " + model.getLastName());
        Matcher lastNameLatLetter = patlatletter.matcher(model.getLastName());
        if (!lastNameLatLetter.matches()) {
            log.info("проверьте фамилия" + model.getFirstName());
            throw new IllegalArgumentException("проверьте ФИО");
        }
    }

    private void birthdateCard(EmployeeCardDTO model) {
        LocalDate date = LocalDate.now();
        log.debug("дата рождения " + model.getBirthdate());
        int age = date.compareTo(model.getBirthdate());
        if (age <= ageMax) {
        } else {
            log.info("проверьте дату рождения" + model.getBirthdate());
            throw new IllegalArgumentException("проверьте дату рождения");
        }
    }


        private void emailCard(EmployeeCardDTO model) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern patEmail = Pattern.compile(regex);
        log.debug("email " + model.getEmail());
        Matcher emailOffers = patEmail.matcher(model.getEmail());
        if (emailOffers.matches()) {
        } else {
            log.info("неверный email" + model.getEmail());
            throw new IllegalArgumentException("неверный email");
        }
    }

    private void phoneNumberCard(EmployeeCardDTO model) {
        String regex = "^(\\d{3}[- .]?){2}\\d{4}$";
        Pattern patPhoneNumber = Pattern.compile(regex);
        log.debug("phoneNumber " + model.getPhoneNumber());
        Matcher phoneNumberCard = patPhoneNumber.matcher(model.getPhoneNumber());
        if (phoneNumberCard.matches() ) {
        } else {
            log.info("неверный телефонный номер" + model.getEmail());
            throw new IllegalArgumentException("неверный телефонный номер: без 8, может выглядить 2055550125, 202 555 0125, 202.555.0125 или 202-555-0125");
        }
    }
}
