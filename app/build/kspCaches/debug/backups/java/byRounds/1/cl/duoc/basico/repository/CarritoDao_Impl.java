package cl.duoc.basico.repository;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.duoc.basico.model.ItemCarrito;
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
public final class CarritoDao_Impl implements CarritoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ItemCarrito> __insertionAdapterOfItemCarrito;

  private final SharedSQLiteStatement __preparedStmtOfEliminarDelCarrito;

  private final SharedSQLiteStatement __preparedStmtOfVaciarCarrito;

  private final SharedSQLiteStatement __preparedStmtOfActualizarCantidad;

  public CarritoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfItemCarrito = new EntityInsertionAdapter<ItemCarrito>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `carrito` (`id`,`productoId`,`nombreProducto`,`precio`,`cantidad`,`usuario`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ItemCarrito entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getProductoId());
        statement.bindString(3, entity.getNombreProducto());
        statement.bindDouble(4, entity.getPrecio());
        statement.bindLong(5, entity.getCantidad());
        statement.bindString(6, entity.getUsuario());
      }
    };
    this.__preparedStmtOfEliminarDelCarrito = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM carrito WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfVaciarCarrito = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM carrito WHERE usuario = ?";
        return _query;
      }
    };
    this.__preparedStmtOfActualizarCantidad = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE carrito SET cantidad = ? WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object agregarAlCarrito(final ItemCarrito item,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfItemCarrito.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object eliminarDelCarrito(final int itemId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfEliminarDelCarrito.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, itemId);
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
          __preparedStmtOfEliminarDelCarrito.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object vaciarCarrito(final String usuario, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfVaciarCarrito.acquire();
        int _argIndex = 1;
        _stmt.bindString(_argIndex, usuario);
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
          __preparedStmtOfVaciarCarrito.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object actualizarCantidad(final int itemId, final int cantidad,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfActualizarCantidad.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cantidad);
        _argIndex = 2;
        _stmt.bindLong(_argIndex, itemId);
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
          __preparedStmtOfActualizarCantidad.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ItemCarrito>> getCarritoByUsuario(final String usuario) {
    final String _sql = "SELECT * FROM carrito WHERE usuario = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, usuario);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"carrito"}, new Callable<List<ItemCarrito>>() {
      @Override
      @NonNull
      public List<ItemCarrito> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfProductoId = CursorUtil.getColumnIndexOrThrow(_cursor, "productoId");
          final int _cursorIndexOfNombreProducto = CursorUtil.getColumnIndexOrThrow(_cursor, "nombreProducto");
          final int _cursorIndexOfPrecio = CursorUtil.getColumnIndexOrThrow(_cursor, "precio");
          final int _cursorIndexOfCantidad = CursorUtil.getColumnIndexOrThrow(_cursor, "cantidad");
          final int _cursorIndexOfUsuario = CursorUtil.getColumnIndexOrThrow(_cursor, "usuario");
          final List<ItemCarrito> _result = new ArrayList<ItemCarrito>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ItemCarrito _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpProductoId;
            _tmpProductoId = _cursor.getInt(_cursorIndexOfProductoId);
            final String _tmpNombreProducto;
            _tmpNombreProducto = _cursor.getString(_cursorIndexOfNombreProducto);
            final float _tmpPrecio;
            _tmpPrecio = _cursor.getFloat(_cursorIndexOfPrecio);
            final int _tmpCantidad;
            _tmpCantidad = _cursor.getInt(_cursorIndexOfCantidad);
            final String _tmpUsuario;
            _tmpUsuario = _cursor.getString(_cursorIndexOfUsuario);
            _item = new ItemCarrito(_tmpId,_tmpProductoId,_tmpNombreProducto,_tmpPrecio,_tmpCantidad,_tmpUsuario);
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
