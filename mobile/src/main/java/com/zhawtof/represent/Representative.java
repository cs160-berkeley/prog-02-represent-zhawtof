package com.zhawtof.represent;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by zhawtof on 3/2/16.
 */

public class Representative implements Serializable {

    public String name;
    public List<String> committees;
    public String recentBill;
    public String position;
    public String pictureName;
    public String partyAffiliation;
    public String endOfTerm;
    public String website;
    public String email;
    public String bioId;

    Representative(String n, List<String> c,
                   String r, String p,
                   String pname, String a, String d) {
        name = n;
        committees = c;
        recentBill = r;
        position = p;
        pictureName = pname;
        partyAffiliation = a;
        endOfTerm = d;
    }

    Representative(String id,
                   String n,
                   String p,
                   String a,
                   String d,
                   String w,
                   String e) {
        bioId = id;
        name = n;
        position = p;
        partyAffiliation = a;
        endOfTerm = d;
        website = w;
        email = e;

        committees = null;
        recentBill = null;
        pictureName = null;

    }

    public static ArrayList<Representative> getDefaults(){
        ArrayList<Representative> repList = new ArrayList<>();
        repList.add(
                new Representative("Michael Bennett",
                        Arrays.asList("Agriculture, Nutrition, Forestry",
                                "Finance", "Health, Education, Labor, and Pensions"),
                        "S. 2500: Health Care Premium Reduction Act",
                        "Senator",
                        "michaelbennet",
                        "D",
                        "April 1, 2017")
        );
        repList.add(
                new Representative("Cory Gardner",
                        Arrays.asList("Commerce, Science, and Transportation", "Foreign Relations",
                                "Energy and Natural Resources", "Small Business and Entrepreneurship"),
                        "S. 2188: Rare Disease Innovation Act",
                        "Senator",
                        "cory_gardner",
                        "R",
                        "April 1, 2017")
        );
        repList.add(
                new Representative("Diana DeGette",
                        Arrays.asList("Committee on Energy and Commerce"),
                        "H.R. 3656: Tobacco to 21 Act",
                        "Representative",
                        "diana_degette",
                        "D",
                        "April 1, 2017")
        );
        return repList;
    }

}
