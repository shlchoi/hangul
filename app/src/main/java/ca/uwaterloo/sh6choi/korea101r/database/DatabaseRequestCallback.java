package ca.uwaterloo.sh6choi.korea101r.database;

/**
 * Created by Samson on 2015-10-23.
 */
public interface DatabaseRequestCallback<T> {
    void processResults(T results);
}
