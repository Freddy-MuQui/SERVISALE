package cl.duoc.basico.repository;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.duoc.basico.model.Producto;
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
public final class ProductoDao_Impl implements ProductoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Producto> __insertionAdapterOfProducto;

  private final EntityDeletionOrUpdateAdapter<Producto> __deletionAdapterOfProducto;

  private final EntityDeletionOrUpdateAdapter<Producto> __updateAdapterOfProducto;

  public ProductoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProducto = new EntityInsertionAdapter<Producto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `producto` (`idProducto`,`nombre`,`descripcion`,`precio`,`cantidadDisponible`,`supermercado`,`categoria`,`esGenerico`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Producto entity) {
        statement.bindLong(1, entity.getIdProducto());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getDescripcion());
        statement.bindDouble(4, entity.getPrecio());
        statement.bindLong(5, entity.getCantidadDisponible());
        statement.bindString(6, entity.getSupermercado());
        statement.bindString(7, entity.getCategoria());
        final int _tmp = entity.getEsGenerico() ? 1 : 0;
        statement.bindLong(8, _tmp);
      }
    };
    this.__deletionAdapterOfProducto = new EntityDeletionOrUpdateAdapter<Producto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `producto` WHERE `idProducto` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Producto entity) {
        statement.bindLong(1, entity.getIdProducto());
      }
    };
    this.__updateAdapterOfProducto = new EntityDeletionOrUpdateAdapter<Producto>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `producto` SET `idProducto` = ?,`nombre` = ?,`descripcion` = ?,`precio` = ?,`cantidadDisponible` = ?,`supermercado` = ?,`categoria` = ?,`esGenerico` = ? WHERE `idProducto` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Producto entity) {
        statement.bindLong(1, entity.getIdProducto());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getDescripcion());
        statement.bindDouble(4, entity.getPrecio());
        statement.bindLong(5, entity.getCantidadDisponible());
        statement.bindString(6, entity.getSupermercado());
        statement.bindString(7, entity.getCategoria());
        final int _tmp = entity.getEsGenerico() ? 1 : 0;
        statement.bindLong(8, _tmp);
        statement.bindLong(9, entity.getIdProducto());
      }
    };
  }

  @Override
  public Object insert(final Producto producto, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfProducto.insert(producto);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final Producto producto, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfProducto.handle(producto);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Producto producto, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfProducto.handle(producto);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Producto>> getAll() {
    final String _sql = "SELECT * FROM producto";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"producto"}, new Callable<List<Producto>>() {
      @Override
      @NonNull
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "idProducto");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfCantidadDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadDisponible");
          final int _cursorIndexOfSupermercado = CursorUtil.getColumnIndexOrThrow(_cursor, "supermercado");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfEsGenerico = CursorUtil.getColumnIndexOrThrow(_cursor, "esGenerico");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Producto _item;
            final int _tmpIdProducto;
            _tmpIdProducto = _cursor.getInt(_cursorIndexOfIdProducto);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final float _tmpPrecio;
            _tmpPrecio = _cursor.getFloat(_cursorIndexOfPrecio);
            final int _tmpCantidadDisponible;
            _tmpCantidadDisponible = _cursor.getInt(_cursorIndexOfCantidadDisponible);
            final String _tmpSupermercado;
            _tmpSupermercado = _cursor.getString(_cursorIndexOfSupermercado);
            final String _tmpCategoria;
            _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            final boolean _tmpEsGenerico;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsGenerico);
            _tmpEsGenerico = _tmp != 0;
            _item = new Producto(_tmpIdProducto,_tmpNombre,_tmpDescripcion,_tmpPrecio,_tmpCantidadDisponible,_tmpSupermercado,_tmpCategoria,_tmpEsGenerico);
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

  @Override
  public Object getAllSync(final Continuation<? super List<Producto>> $completion) {
    final String _sql = "SELECT * FROM producto";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Producto>>() {
      @Override
      @NonNull
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "idProducto");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfCantidadDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadDisponible");
          final int _cursorIndexOfSupermercado = CursorUtil.getColumnIndexOrThrow(_cursor, "supermercado");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfEsGenerico = CursorUtil.getColumnIndexOrThrow(_cursor, "esGenerico");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Producto _item;
            final int _tmpIdProducto;
            _tmpIdProducto = _cursor.getInt(_cursorIndexOfIdProducto);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final float _tmpPrecio;
            _tmpPrecio = _cursor.getFloat(_cursorIndexOfPrecio);
            final int _tmpCantidadDisponible;
            _tmpCantidadDisponible = _cursor.getInt(_cursorIndexOfCantidadDisponible);
            final String _tmpSupermercado;
            _tmpSupermercado = _cursor.getString(_cursorIndexOfSupermercado);
            final String _tmpCategoria;
            _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            final boolean _tmpEsGenerico;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsGenerico);
            _tmpEsGenerico = _tmp != 0;
            _item = new Producto(_tmpIdProducto,_tmpNombre,_tmpDescripcion,_tmpPrecio,_tmpCantidadDisponible,_tmpSupermercado,_tmpCategoria,_tmpEsGenerico);
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

  @Override
  public Object getById(final int id, final Continuation<? super Producto> $completion) {
    final String _sql = "SELECT * FROM producto WHERE idProducto = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Producto>() {
      @Override
      @Nullable
      public Producto call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "idProducto");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfCantidadDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadDisponible");
          final int _cursorIndexOfSupermercado = CursorUtil.getColumnIndexOrThrow(_cursor, "supermercado");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfEsGenerico = CursorUtil.getColumnIndexOrThrow(_cursor, "esGenerico");
          final Producto _result;
          if (_cursor.moveToFirst()) {
            final int _tmpIdProducto;
            _tmpIdProducto = _cursor.getInt(_cursorIndexOfIdProducto);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final float _tmpPrecio;
            _tmpPrecio = _cursor.getFloat(_cursorIndexOfPrecio);
            final int _tmpCantidadDisponible;
            _tmpCantidadDisponible = _cursor.getInt(_cursorIndexOfCantidadDisponible);
            final String _tmpSupermercado;
            _tmpSupermercado = _cursor.getString(_cursorIndexOfSupermercado);
            final String _tmpCategoria;
            _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            final boolean _tmpEsGenerico;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsGenerico);
            _tmpEsGenerico = _tmp != 0;
            _result = new Producto(_tmpIdProducto,_tmpNombre,_tmpDescripcion,_tmpPrecio,_tmpCantidadDisponible,_tmpSupermercado,_tmpCategoria,_tmpEsGenerico);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Producto>> getByCategoria(final String categoria) {
    final String _sql = "SELECT * FROM producto WHERE categoria = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, categoria);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"producto"}, new Callable<List<Producto>>() {
      @Override
      @NonNull
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "idProducto");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfCantidadDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadDisponible");
          final int _cursorIndexOfSupermercado = CursorUtil.getColumnIndexOrThrow(_cursor, "supermercado");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfEsGenerico = CursorUtil.getColumnIndexOrThrow(_cursor, "esGenerico");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Producto _item;
            final int _tmpIdProducto;
            _tmpIdProducto = _cursor.getInt(_cursorIndexOfIdProducto);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final float _tmpPrecio;
            _tmpPrecio = _cursor.getFloat(_cursorIndexOfPrecio);
            final int _tmpCantidadDisponible;
            _tmpCantidadDisponible = _cursor.getInt(_cursorIndexOfCantidadDisponible);
            final String _tmpSupermercado;
            _tmpSupermercado = _cursor.getString(_cursorIndexOfSupermercado);
            final String _tmpCategoria;
            _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            final boolean _tmpEsGenerico;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsGenerico);
            _tmpEsGenerico = _tmp != 0;
            _item = new Producto(_tmpIdProducto,_tmpNombre,_tmpDescripcion,_tmpPrecio,_tmpCantidadDisponible,_tmpSupermercado,_tmpCategoria,_tmpEsGenerico);
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

  @Override
  public Flow<List<Producto>> getBySupermercado(final String supermercado) {
    final String _sql = "SELECT * FROM producto WHERE supermercado = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, supermercado);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"producto"}, new Callable<List<Producto>>() {
      @Override
      @NonNull
      public List<Producto> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "idProducto");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfDescripcion = CursorUtil.getColumnIndexOrThrow(_cursor, "descripcion");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfCantidadDisponible = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidadDisponible");
          final int _cursorIndexOfSupermercado = CursorUtil.getColumnIndexOrThrow(_cursor, "supermercado");
          final int _cursorIndexOfCategoria = CursorUtil.getColumnIndexOrThrow(_cursor, "categoria");
          final int _cursorIndexOfEsGenerico = CursorUtil.getColumnIndexOrThrow(_cursor, "esGenerico");
          final List<Producto> _result = new ArrayList<Producto>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Producto _item;
            final int _tmpIdProducto;
            _tmpIdProducto = _cursor.getInt(_cursorIndexOfIdProducto);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpDescripcion;
            _tmpDescripcion = _cursor.getString(_cursorIndexOfDescripcion);
            final float _tmpPrecio;
            _tmpPrecio = _cursor.getFloat(_cursorIndexOfPrecio);
            final int _tmpCantidadDisponible;
            _tmpCantidadDisponible = _cursor.getInt(_cursorIndexOfCantidadDisponible);
            final String _tmpSupermercado;
            _tmpSupermercado = _cursor.getString(_cursorIndexOfSupermercado);
            final String _tmpCategoria;
            _tmpCategoria = _cursor.getString(_cursorIndexOfCategoria);
            final boolean _tmpEsGenerico;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfEsGenerico);
            _tmpEsGenerico = _tmp != 0;
            _item = new Producto(_tmpIdProducto,_tmpNombre,_tmpDescripcion,_tmpPrecio,_tmpCantidadDisponible,_tmpSupermercado,_tmpCategoria,_tmpEsGenerico);
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

  @Override
  public Object getCategorias(final Continuation<? super List<String>> $completion) {
    final String _sql = "SELECT DISTINCT categoria FROM producto";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<String>>() {
      @Override
      @NonNull
      public List<String> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final List<String> _result = new ArrayList<String>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final String _item;
            _item = _cursor.getString(0);
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
