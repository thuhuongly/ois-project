/**
 * 
 */
package ois.wise.vub.ac.be;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * @author thuhuongly
 *
 */
public class Main {
	public static void main(String[] args) throws Exception {
		// Read an RDF file
		Model model = ModelFactory.createDefaultModel();
		model.read("OISproject.owl");
		String queryString = "SELECT DISTINCT ?n {?x a <&webprotege2;project/86OZkbUV0i6KuwfaHFOYOU#Dad>."
												+ "?x <&webprotege2;project/86OZkbUV0i6KuwfaHFOYOU#hasChild> ?n.}";
		Query query = QueryFactory.create(queryString);
		System.out.println("Without reasoning:");
		try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				Literal l = soln.getLiteral("n");
				System.out.println(l.getString());
			}
		}
		
		System.out.println("With reasoning:");
		InfModel inf = ModelFactory.createRDFSModel(model);
		try (QueryExecution qexec = QueryExecutionFactory.create(query, inf)) {
			ResultSet results = qexec.execSelect();
			while (results.hasNext()) {
				QuerySolution soln = results.nextSolution();
				Literal l = soln.getLiteral("n");
				System.out.println(l.getString());
			}
		}
	}
}
