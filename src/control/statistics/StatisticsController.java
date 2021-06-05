package control.statistics;

import view.function.statistics.StatisticsPanel;

public class StatisticsController implements StatisticsControllerInterface {
    
    private volatile static StatisticsController uniqueInstance;
    
    private StatisticsPanel view;
    
    private StatisticsController() {
        this.view = StatisticsPanel.getInstance(this);
    }
    
    public static StatisticsControllerInterface getInstance() {
        if (uniqueInstance == null) {
            synchronized(StatisticsController.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new StatisticsController();
                }
            }
        }
        return uniqueInstance;
    }
    
    @Override
    public void requestViewRevenue() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void requestViewIngredientCost() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void requestViewProductState() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void requestViewEmployee() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
