package moteur.donne.evenement.mobile;

import java.util.ArrayList;
import java.util.List;

import config.ConfigurationEvenement;
import moteur.donne.carte.Bloc;
import moteur.donne.evenement.Evenement;
import moteur.processus.visitor.EvenementVisitor;

public class GroupePluie extends Evenement {

    private ArrayList<Pluie> pluieUnitaires;
    private static final int TAILLE_GROUPE = 3;

    public GroupePluie(Bloc position, double dureeRestante, int impactTemperature, int impactHumidite,
            int impactPollution, int impactPurification) {
        super(position, dureeRestante, impactTemperature, impactHumidite, impactPollution, impactPurification);
        this.pluieUnitaires = new ArrayList<>();

        for (int i = 0; i < TAILLE_GROUPE; i++) {
            Bloc posPluie = new Bloc(position.getX() + i, position.getY());
            Pluie pluie = new Pluie(posPluie, dureeRestante, impactTemperature, impactHumidite,
                    impactPollution, impactPurification);
            pluieUnitaires.add(pluie);
        }
    }

    public List<Pluie> getPluieUnitaires() {
        return pluieUnitaires;
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
        return false;
    }

    public static int getTailleGroupe() {
        return TAILLE_GROUPE;
    }
}
