package moteur.donne.evenement.mobile;

import java.util.ArrayList;
import java.util.List;

import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.EvenementVisitor;

public class GroupePluieAcide extends Evenement {

    private ArrayList<PluieAcide> pluieAcideUnitaires;
    private static final int TAILLE_GROUPE = 3;

    public GroupePluieAcide(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite,
            int impactPollution, int impactPurification) {
        super(position, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
        this.pluieAcideUnitaires = new ArrayList<>();

        for (int i = 0; i < TAILLE_GROUPE; i++) {
            Bloc posPluie = new Bloc(position.getX() + i, position.getY());
            PluieAcide pluie = new PluieAcide(posPluie, dureeRestante, impactTemperature, impactHumidite,
                    impactPollution, impactPurification);
            pluieAcideUnitaires.add(pluie);
        }
    }

    public List<PluieAcide> getPluieAcideUnitaires() {
        return pluieAcideUnitaires;
    }

    @Override
    public void accept(EvenementVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public boolean isPluie() {
        return true;
    }

    @Override
    public boolean isPluieAcide() {
        return true;
    }

    public static int getTailleGroupe() {
        return TAILLE_GROUPE;
    }
}
