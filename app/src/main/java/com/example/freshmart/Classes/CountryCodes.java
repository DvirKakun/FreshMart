package com.example.freshmart.Classes;

import java.util.Arrays;
import java.util.List;

public class CountryCodes {
    public static List<String> GetCountryCodes()
    {
        List<String> countryCodes = Arrays.asList(
                // North America
                "+1",   // United States, Canada

                // Western Europe
                "+44",  // United Kingdom
                "+49",  // Germany
                "+33",  // France
                "+34",  // Spain
                "+39",  // Italy
                "+31",  // Netherlands
                "+32",  // Belgium
                "+41",  // Switzerland
                "+43",  // Austria
                "+351", // Portugal
                "+353", // Ireland

                // Nordics
                "+46",  // Sweden
                "+45",  // Denmark
                "+47",  // Norway
                "+358", // Finland
                "+354", // Iceland

                // Eastern Europe
                "+48",  // Poland
                "+36",  // Hungary
                "+420", // Czech Republic
                "+421", // Slovakia
                "+40",  // Romania
                "+371", // Latvia
                "+372", // Estonia
                "+370", // Lithuania
                "+380", // Ukraine
                "+375", // Belarus
                "+373", // Moldova

                // Southern Europe
                "+30",  // Greece
                "+357", // Cyprus
                "+378", // San Marino
                "+386", // Slovenia
                "+385", // Croatia
                "+387", // Bosnia and Herzegovina
                "+389", // North Macedonia
                "+382", // Montenegro
                "+381", // Serbia
                "+383", // Kosovo
                "+356", // Malta
                "+350", // Gibraltar

                // Other European Regions
                "+378", // Andorra
                "+352", // Luxembourg
                "+423", // Liechtenstein

                // **Asia**
                "+60",  // Malaysia
                "+61",  // Australia
                "+62",  // Indonesia
                "+63",  // Philippines
                "+64",  // New Zealand
                "+65",  // Singapore
                "+66",  // Thailand
                "+81",  // Japan
                "+82",  // South Korea
                "+84",  // Vietnam
                "+86",  // China
                "+90",  // Turkey
                "+91",  // India
                "+92",  // Pakistan
                "+93",  // Afghanistan
                "+94",  // Sri Lanka
                "+95",  // Myanmar (Burma)
                "+98",  // Iran
                "+212", // Morocco (Africa, but close to Asia)
                "+962", // Jordan
                "+963", // Syria
                "+964", // Iraq
                "+965", // Kuwait
                "+966", // Saudi Arabia
                "+967", // Yemen
                "+971", // United Arab Emirates
                "+972", // Israel
                "+973", // Bahrain
                "+974", // Qatar
                "+975", // Bhutan
                "+976", // Mongolia
                "+977", // Nepal
                "+98",  // Iran
                "+880", // Bangladesh
                "+981", // Maldives
                "+994", // Azerbaijan
                "+992", // Tajikistan
                "+993", // Turkmenistan
                "+994", // Azerbaijan
                "+995", // Georgia
                "+996", // Kyrgyzstan
                "+998"  // Uzbekistan
        );

         countryCodes.sort((firstCode, secondCode) -> {
            int firstCodeAsInt = Integer.parseInt(firstCode.substring(1));
            int secondCodeAsInt = Integer.parseInt(secondCode.substring(1));

            return Integer.compare(firstCodeAsInt, secondCodeAsInt);
        });

         return countryCodes;
    }
}
