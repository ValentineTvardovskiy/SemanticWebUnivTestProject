package com.example.semanticapp.jena;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.VCARD;

public class ModelResource {

    public static void create(String name, String surname) {
        String personURI = "http://somewhere/" + name + surname;
        String fullName = name + " " + surname;

        Resource person = RDF.model.createResource(personURI);

        person.addProperty(VCARD.FN, fullName)
                .addProperty(VCARD.N,
                        RDF.model.createResource()
                                .addProperty(VCARD.Given, name)
                                .addProperty(VCARD.Family, surname));

    }
}
