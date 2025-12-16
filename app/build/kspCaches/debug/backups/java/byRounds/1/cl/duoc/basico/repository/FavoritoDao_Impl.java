package cl.duoc.basico.repository;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.duoc.basico.model.Favorito;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoritoDao_Impl implements FavoritoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Favorito> __insertionAdapterOfFavorito;

  private final EntityDeletionOrUpdateAdapter<Favorito> __deletionAdapterOfFavorito;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByUsuarioAndProducto;

  public FavoritoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavorito = new EntityInsertionAdapter<Favorito>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorito` (`idFavorito`,`usuario`,`producto`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Favorito entity) {
        statement.bindLong(1, entity.getIdFavorito());
        statement.bindString(2, entity.getUsuario());
        statement.bindString(3, entity.getProducto());
      }
    };
    this.__deletionAdapterOfFavorito = new EntityDeletionOrUpdateAdapter<Favorito>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorito` WHERE `idFavorito` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Favorito entity) {
        statement.bindLong(1, entity.getIdFavorito());
      }
    };
    this.__preparedStmtOfDeleteByUsuarioAndProducto = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM favorito WHERE usuario = ? AND producto = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final Favorito favorito, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavorito.insert(favorito);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Favorito favorito, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavorito.handle(favorito);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByUsuarioAndProducto(final String usuario, final String producto,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByUsuarioAndProducto.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, usuario);
        _argIndex = 2;
        _stmt.bindString(_argIndex, producto);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteByUsuarioAndProducto.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllByUsuario(final String usuario,
      final Continuation<? super List<Favorito>> $completion) {
    final String _sql = "SELECT * FROM favorito WHERE usuario = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, usuario);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Favorito>>() {
      @Override
      @NonNull
      public List<Favorito> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdFavorito = CursorUtil.getColumnIndexOrThrow(_cursor, "idFavorito");
          final int _cursorIndexOfUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "usuario");
          final int _cursorIndexOfProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "producto");
          final List<Favorito> _result = new ArrayList<Favorito>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Favorito _item;
            final int _tmpIdFavorito;
            _tmpIdFavorito = _cursor.getInt(_cursorIndexOfIdFavorito);
            final String _tmpUsuario;
            _tmpUsuario = _cursor.getString(_cursorIndexOfUsuario);
            final String _tmpProducto;
            _tmpProducto = _cursor.getString(_cursorIndexOfProducto);
            _item = new Favorito(_tmpIdFavorito,_tmpUsuario,_tmpProducto);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
