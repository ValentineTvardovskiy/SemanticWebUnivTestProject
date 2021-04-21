package com.example.semanticapp.jena;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.VCARD;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ModelResource {

    public static void create(String name, String surname) {
        String personURI = "http://somewhere/1";
        String fullName = name + surname;

        Resource person = RDF.model.createResource(personURI);

        person.addProperty(VCARD.N,
                RDF.model.createResource()
                        .addProperty(VCARD.Given, name)
                        .addProperty(VCARD.Family, surname));
        person.addProperty(VCARD.FN, fullName);
    }

    public static void update(String name, String surname) {
        String personURI = "http://somewhere/1";
        String fullName = name + surname;

        Resource person = RDF.model.getResource(personURI);
        person.getProperty(VCARD.FN).changeObject(fullName);
        Statement property = person.getProperty(VCARD.N);
        property.getProperty(VCARD.Given).changeObject(name);
        property.getProperty(VCARD.Family).changeObject(surname);
    }

    public static void delete() {
        RDF.model.removeAll();
    }

    public static void generateForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getContextPath();
        String updatePath = path + "/update-resource";
        String deletePath = path + "/delete-resource";

        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ResourceController</title>");
            out.println("<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Resource management at " + request.getContextPath() + "</h1>");
            RDF.model.write(out);
            out.println("<form action=\"" + updatePath + "\" method=\"post\">");
            out.println("<input type=\"text\" name=\"name\" placeholder=\"enter new name\"/> <br> ");
            out.println("<input type=\"text\" name=\"surname\" placeholder=\"enter new surname\"/> <br>");
            out.println("<button type=\"submit\" class=\"btn btn-primary\">Update</button> ");
            out.println("<a type=\"button\" class=\"btn btn-outline-info\" href = \"" + deletePath + "\"> Delete resource </a>");
            out.println("<a type=\"button\" class=\"btn btn-outline-info\" href = \"" + path + "\"> Home </a>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
