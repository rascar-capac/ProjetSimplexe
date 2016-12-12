package modele;
import java.util.*;

/**
 * La classe Interaction permet de gérer toutes les interactions avec l'utilisateur. 
 * Elle est caractérisée par une matrice qu'elle va créer sur base des informations fournies par l'utilisateur.
 *
 * @author Nicolas Verhaeghe
 * @author Théo Constant
 * @author Florian Vangaeveren
 */
public class Interaction {
	
	/**
	 * La matrice qu'elle crée et qu'elle remplit.
	 */
	private Matrice matrice;
	
	/**
	 * Constructeur Interaction.
	 * Il instancie la matrice.
	 */
	public Interaction() {
		this.matrice = new Matrice();
	}
	
	/**
	 * Demande des données à l'utilisateur.
	 */
	public void demanderInfos() {
		int varNb = 0, contrNb = 0;
		Scanner scan = new Scanner(System.in);
		String tmpCoeff;

		//test sur le nombre de variables
		while(varNb<1) {
			System.out.print("Entrez le nombre de contraintes: ");
			try {
				varNb = scan.nextInt();
			}
			catch(Exception e) {
				varNb = -1;
				scan.next();
			}
			if(varNb<1)
				System.out.println("Nombre de variables incorrect.");
		}

		//test sur le nombre de contraintes
		while(contrNb<1) {
			System.out.print("Entrez le nombre de contraintes: ");
			try {
				contrNb = scan.nextInt();
			}
			catch(Exception e) {
				contrNb = -1;
				scan.next();
			}
			if(contrNb<1)
				System.out.println("Nombre de contraintes incorrect.");
		}
		//liste temporaire à laquelle on ajoute les contraintes ainsi que la fonction objectif un à un
		List<Double> listeCoeff = new ArrayList<Double>();

		//ajout de la fonction objectif
		listeCoeff.clear();
		System.out.println("Entrez les " + varNb + " coefficients des variables de la fonction objectif");
		System.out.println("Exemple: 1,4,3.5");
		if(varNb==1)
			tmpCoeff = "1,4";
		else
			tmpCoeff = "";
			
		while(tmpCoeff.split(",").length!=varNb) {
			try {
				System.out.print("Coefficients de la fonction objectif: ");
				tmpCoeff = scan.next();
			}
			catch(Exception e) {
				System.out.println("Fonction objectif entrée incorrectement.");
			}
			if(tmpCoeff.split(",").length!=incNb)
				System.out.println("Le nombre de termes insérés ("+tmpCoeff.split(",").length+") n'est pas égal à celui attendu ("+varNb+").");
		}

		for(int i=0;i<varNb;i++) {
			listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[i]));
		}
		//sauvegarde des coefficients de la fonction objectif pour l'ajouter plus tard dans la matrice
		List<Double> objectif = new ArrayList<Double>(listeCoeff);

		System.out.println("Entrez les " + varNb + " coefficients des variables suivis du terme indépendant des contraintes, séparés par des virgules.");
		System.out.println("Exemple: 2,7,-3,18");

		//ajout des coefficients pour chaque contrainte
		for(int i=0;i<ineqNb;i++) {
			tmpCoeff="";
			listeCoeff.clear();
			while(tmpCoeff.split(",").length!=incNb+1) {
				System.out.print("Inéquation n°"+(i+1)+": ");
				tmpCoeff = scan.next();
				if(tmpCoeff.split(",").length!=incNb+1) {
					System.out.println("Le nombre de termes insérés ("+tmpCoeff.split(",").length+") n'est pas égal à celui attendu ("+(incNb+1)+").");
				}
			}
			for(int j=0;j<incNb+1;j++){
				listeCoeff.add(Double.parseDouble(tmpCoeff.split(",")[j]));
			}
			this.matrice.ajouterLigne(new ArrayList<Double>(listeCoeff));
		}

		//ajout de la fonction objectif dans la matrice à l'aide de la liste créée précédemment
		this.matrice.ajouterLigne(new ArrayList<Double>(objectif));
		scan.close();
	}
	
	/**
	 * Appelle la méthode calculerSolution de la classe Simplexe et affiche la chaîne qu'elle retourne.
	 */
	public void executerSimplexe() {
		System.out.println(Simplexe.calculerSolution(this.matrice));
	}
}