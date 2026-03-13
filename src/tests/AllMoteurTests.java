package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
    tests.moteur.TestCarte.class,
    tests.moteur.TestBloc.class,
    tests.moteur.TestBiome.class,
    tests.moteur.TestBiomeFactory.class,
    tests.moteur.TestEvenementFactory.class,
    tests.moteur.TestManageur.class,
    tests.moteur.TestRegleTransformation.class,
    tests.moteur.TestPerformance.class,
    tests.moteur.TestRobustesse.class
})
public class AllMoteurTests {
}
