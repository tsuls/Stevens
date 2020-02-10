package edu.stevens.cs522.chatserver.async;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import edu.stevens.cs522.chatserver.managers.TypedCursor;

/**
 * Created by dduggan.
 */

public class QueryBuilder<T> implements LoaderManager.LoaderCallbacks<Cursor> {

    // TODO complete the implementation of this

    private String tag;

    private String[] columns;

    private String select;

    private String[] selectArgs;

    private String order;

    private IEntityCreator<T> creator;

    private IQueryListener<T> listener;

    private Context context;

    private Uri uri;

    private int loaderID;

    private QueryBuilder(String tag,
                         Context context,
                         Uri uri,
                         String[] columns,
                         String select,
                         String[] selectArgs,
                         String order,
                         int loaderID,
                         IEntityCreator<T> creator,
                         IQueryListener<T> listener) {
        // TODO
        this.tag = tag;
        this.columns = columns;
        this.select = select;
        this.selectArgs = selectArgs;
        this.order = order;
        this.creator = creator;
        this.listener = listener;
        this.context = context;
        this.uri = uri;
        this.loaderID = loaderID;
    }

    public static <T> void executeQuery(String tag,
                                        Activity context,
                                        Uri uri,
                                        String[] columns,
                                        String select,
                                        String[] selectArgs,
                                        String order,
                                        int loaderID,
                                        IEntityCreator<T> creator,
                                        IQueryListener<T> listener) {

        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, columns, select, selectArgs, order,
                loaderID, creator, listener);

        LoaderManager lm = context.getLoaderManager();

        lm.initLoader(loaderID, null, qb);
    }

    public static <T> void executeQuery(String tag,
                                        Activity context,
                                        Uri uri,
                                        int loaderID,
                                        IEntityCreator<T> creator,
                                        IQueryListener<T> listener) {

        executeQuery(tag, context, uri, null, null, null, null, loaderID, creator, listener);
    }

    public static <T> void reexecuteQuery(String tag,
                                          Activity context,
                                          Uri uri,
                                          String[] columns,
                                          String select,
                                          String[] selectArgs,
                                          String order,
                                          int loaderID,
                                          IEntityCreator<T> creator,
                                          IQueryListener<T> listener) {

        QueryBuilder<T> qb = new QueryBuilder<T>(tag, context,
                uri, columns, select, selectArgs, order,
                loaderID, creator, listener);

        LoaderManager lm = context.getLoaderManager();

        lm.restartLoader(loaderID, null, qb);
    }

    public static <T> void reexecuteQuery(String tag,
                                          Activity context,
                                          Uri uri,
                                          int loaderID,
                                          IEntityCreator<T> creator,
                                          IQueryListener<T> listener) {

        reexecuteQuery(tag, context, uri, null, null, null, null, loaderID, creator, listener);
    }


    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        // TODO
        return new CursorLoader(context, uri, columns, select, selectArgs, order);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data) {
        // TODO
        listener.handleResults(new TypedCursor<T>(data, creator));
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // TODO
        listener.closeResults();
    }
}
