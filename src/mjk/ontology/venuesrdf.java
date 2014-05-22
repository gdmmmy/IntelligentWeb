package mjk.ontology;

import java.io.FileWriter;
import java.io.IOException;

import mjk.model.VenuesModel;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;

public class venuesrdf {
	// some definitions
	public venuesrdf(VenuesModel vm) throws IOException{
	String venueURI    = "http://MJKTwitterQuery/"+vm.getName();
	String venuename     = vm.getName();
	System.out.println("rdf first test");
	// create an empty Model
	Model model = ModelFactory.createDefaultModel();

	// create the resource
	Resource venue= model.createResource(venueURI);

	// add the property
	venue.addProperty(VCARD.FN, venuename);
//	venue.addProperty(VCARD.FN, vm.getcategory());
	
	model.write(System.out, "RDF/XML");
	String fileName = "Test111.rdf";
	FileWriter out = new FileWriter( fileName );
	model.write(out, "RDF/XML-ABBREV");
}
	
}
