package cl.duoc.basico.repository;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.duoc.basico.model.Supermercado;
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
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SupermercadoDao_Impl implements SupermercadoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Supermercado> __insertionAdapterOfSupermercado;

  public SupermercadoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSupermercado = new EntityInsertionAdapter<Supermercado>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `supermercado` (`idSupermercado`,`nombre`,`latitud`,`longitud`,`direccion`,`comuna`,`region`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Supermercado entity) {
        statement.bindLong(1, entity.getIdSupermercado());
        statement.bindString(2, entity.getNombre());
        statement.bindDouble(3, entity.getLatitud());
        statement.bindDouble(4, entity.getLongitud());
        statement.bindString(5, entity.getDireccion());
        statement.bindString(6, entity.getComuna());
        statement.bindString(7, entity.getRegion());
      }
    };
  }

  @Override
  public Object insert(final Supermercado supermercado,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfSupermercado.insert(supermercado);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Supermercado>> getAll() {
    final String _sql = "SELECT * FROM supermercado";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"supermercado"}, new Callable<List<Supermercado>>() {
      @Override
      @NonNull
      public List<Supermercado> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdSupermercado = CursorUtil.getColumnIndexOrThrow(_cursor, "idSupermercado");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfLatitud = CursorUtil.getColumnIndexOrThrow(_cursor, "latitud");
          final int _cursorIndexOfLongitud = CursorUtil.getColumnIndexOrThrow(_cursor, "longitud");
          final int _cursorIndexOfDireccion = CursorUtil.getColumnIndexOrThrow(_cursor, "direccion");
          final int _cursorIndexOfComuna = CursorUtil.getColumnIndexOrThrow(_cursor, "comuna");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final List<Supermercado> _result = new ArrayList<Supermercado>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Supermercado _item;
            final int _tmpIdSupermercado;
            _tmpIdSupermercado = _cursor.getInt(_cursorIndexOfIdSupermercado);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final double _tmpLatitud;
            _tmpLatitud = _cursor.getDouble(_cursorIndexOfLatitud);
            final double _tmpLongitud;
            _tmpLongitud = _cursor.getDouble(_cursorIndexOfLongitud);
            final String _tmpDireccion;
            _tmpDireccion = _cursor.getString(_cursorIndexOfDireccion);
            final String _tmpComuna;
            _tmpComuna = _cursor.getString(_cursorIndexOfComuna);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            _item = new Supermercado(_tmpIdSupermercado,_tmpNombre,_tmpLatitud,_tmpLongitud,_tmpDireccion,_tmpComuna,_tmpRegion);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
