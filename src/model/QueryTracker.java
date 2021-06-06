package model;

import java.util.ArrayList;
import java.util.Iterator;
import org.javatuples.Pair;

public abstract class QueryTracker {

    enum QueryType {
        INSERT,
        UPDATE,
        DELETE,
    }

    private ArrayList<Pair<DatabaseModel, QueryType>> queries;

    public void setModels(Iterator<DatabaseModel> iterator) {
        queries.clear();
        while (iterator.hasNext()) {
            queries.add(new Pair<>(iterator.next(), null));
        }
    }

    public ArrayList<DatabaseModel> getModels() {
        ArrayList<DatabaseModel> models = new ArrayList<>();
        for (Pair<DatabaseModel, QueryType> query : queries) {
            if (query.getValue1() != QueryType.DELETE) {
                models.add(query.getValue0());
            }
        }
        return models;
    }

    public void addInsertQuery(DatabaseModel model) {
        for (Pair<DatabaseModel, QueryType> query : queries) {
            if (query.getValue0().equals(model)) {
                if (query.getValue1() == QueryType.DELETE) {
                    updateModel(query.getValue0(), model);
                    query.setAt1(QueryType.INSERT);
                } else {
                    // if query is update or insert
                    updateModel(query.getValue0(), model);
                }
                return;
            }
        }
        queries.add(new Pair<>(createInstance(model), QueryType.INSERT));
    }

    public void addDeleteQuery(DatabaseModel model) {
        for (Pair<DatabaseModel, QueryType> query : queries) {
            if (query.getValue0().equals(model)) {
                if (query.getValue1() == QueryType.INSERT) {
                    queries.remove(query);
                } else if (query.getValue1() == QueryType.UPDATE) {
                    query.setAt1(QueryType.DELETE);
                }
                return;
            }
        }
        throw new IllegalArgumentException("Model is unexisted.");
    }

    public void addUpdateQuery(DatabaseModel model) {
        for (Pair<DatabaseModel, QueryType> query : queries) {
            if (query.getValue0().equals(model)) {
                updateModel(query.getValue0(), model);
                return;
            }
        }
    }

    public void executeQuery() {
        for (Pair<DatabaseModel, QueryType> query : queries) {
            QueryType queryType = query.getValue1();
            switch (queryType) {
                case INSERT: {
                    query.getValue0().insertToDatabase();
                    break;
                }
                case UPDATE: {
                    query.getValue0().updateInDatabase();
                    break;
                }
                case DELETE: {
                    query.getValue0().deleteInDatabase();
                    break;
                }
            }
        }
    }

    public abstract void updateModel(DatabaseModel oldModel, DatabaseModel newModel);

    public abstract DatabaseModel createInstance(DatabaseModel model);
    
}
