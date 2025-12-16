package cl.duoc.basico.repository;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import cl.duoc.basico.model.Comuna;
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
public final class ComunaDao_Impl implements ComunaDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Comuna> __insertionAdapterOfComuna;

  public ComunaDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfComuna = new EntityInsertionAdapter<Comuna>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `comuna` (`idComuna`,`nombre`,`region`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Comuna entity) {
        statement.bindLong(1, entity.getIdComuna());
        statement.bindString(2, entity.getNombre());
        statement.bindString(3, entity.getRegion());
      }
    };
  }

  @Override
  public Object insert(final Comuna comuna, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfComuna.insert(comuna);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAll(final Continuation<? super List<Comuna>> $completion) {
    final String _sql = "SELECT * FROM comuna";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Comuna>>() {
      @Override
      @NonNull
      public List<Comuna> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfIdComuna = CursorUtil.getColumnIndexOrThrow(_cursor, "idComuna");
          final int _cursorIndexOfNombre = CursorUtil.getColumnIndexOrThrow(_cursor, "nombre");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final List<Comuna> _result = new ArrayList<Comuna>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Comuna _item;
            final int _tmpIdComuna;
            _tmpIdComuna = _cursor.getInt(_cursorIndexOfIdComuna);
            final String _tmpNombre;
            _tmpNombre = _cursor.getString(_cursorIndexOfNombre);
            final String _tmpRegion;
            _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            _item = new Comuna(_tmpIdComuna,_tmpNombre,_tmpRegion);
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
