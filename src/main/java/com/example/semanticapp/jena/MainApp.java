package com.example.semanticapp.jena;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.VCARD;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainApp {

    static String PERSON_URI = "https://github.com/ValentineTvardovskiy";
    static String FULL_NAME = "Valentine Tvardovskiy";
    static String GIVEN_NAME = "Valik";
    static String FAMILY_NAME = "Tvardovskiy";

    public static void main(String[] args) throws FileNotFoundException {

        Model originalModel = ModelFactory.createDefaultModel();
        Resource person = originalModel.createResource(PERSON_URI);

        person.addProperty(VCARD.FN, FULL_NAME)
                .addProperty(VCARD.N,
                        originalModel.createResource()
                                .addProperty(VCARD.Given, GIVEN_NAME)
                                .addProperty(VCARD.Family, FAMILY_NAME));

        StmtIterator iter = originalModel.listStatements();
        while (iter.hasNext()) {
            Statement stmt = iter.nextStatement();
            Resource  subject = stmt.getSubject();
            Property predicate = stmt.getPredicate();
            RDFNode object = stmt.getObject();

            System.out.print(subject.toString());
            System.out.print(" " + predicate.toString() + " ");
            if (object instanceof Resource) {
                System.out.print(object.toString());
            } else {
                System.out.print(" \"" + object.toString() + "\"");
            }

            System.out.println(" .");
            System.out.println("============ originalModel.write ==============");
            originalModel.write(System.out);


            OutputStream outStream = new FileOutputStream("person.xml");

            RDFDataMgr.write(outStream, originalModel, Lang.RDFXML);



            // create an empty originalModel
            Model parsedModel = ModelFactory.createDefaultModel();

            // use the RDFDataMgr to find the input file
            InputStream in = RDFDataMgr.open( "person.xml");
            if (in == null) {
                throw new IllegalArgumentException(
                        "File: " + "person.xml" + " not found");
            }

            parsedModel.read(in, null);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++");
            parsedModel.write(System.out);
        }
    }
}
