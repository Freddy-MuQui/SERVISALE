package cl.duoc.basico.repository;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile ProductoDao _productoDao;

  private volatile UsuarioDao _usuarioDao;

  private volatile FavoritoDao _favoritoDao;

  private volatile SupermercadoDao _supermercadoDao;

  private volatile RegionDao _regionDao;

  private volatile ComunaDao _comunaDao;

  private volatile CarritoDao _carritoDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `producto` (`idProducto` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `descripcion` TEXT NOT NULL, `precio` REAL NOT NULL, `cantidadDisponible` INTEGER NOT NULL, `supermercado` TEXT NOT NULL, `categoria` TEXT NOT NULL, `esGenerico` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `usuario` (`idUsuario` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `email` TEXT NOT NULL, `password` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `favorito` (`idFavorito` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `usuario` TEXT NOT NULL, `producto` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `supermercado` (`idSupermercado` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `latitud` REAL NOT NULL, `longitud` REAL NOT NULL, `direccion` TEXT NOT NULL, `comuna` TEXT NOT NULL, `region` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `region` (`idRegion` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `comuna` (`idComuna` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nombre` TEXT NOT NULL, `region` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `carrito` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `productoId` INTEGER NOT NULL, `nombreProducto` TEXT NOT NULL, `precio` REAL NOT NULL, `cantidad` INTEGER NOT NULL, `usuario` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '188e2984d8f81964ae27caee6812bf52')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `producto`");
        db.execSQL("DROP TABLE IF EXISTS `usuario`");
        db.execSQL("DROP TABLE IF EXISTS `favorito`");
        db.execSQL("DROP TABLE IF EXISTS `supermercado`");
        db.execSQL("DROP TABLE IF EXISTS `region`");
        db.execSQL("DROP TABLE IF EXISTS `comuna`");
        db.execSQL("DROP TABLE IF EXISTS `carrito`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsProducto = new HashMap<String, TableInfo.Column>(8);
        _columnsProducto.put("idProducto", new TableInfo.Column("idProducto", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("descripcion", new TableInfo.Column("descripcion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("precio", new TableInfo.Column("precio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("cantidadDisponible", new TableInfo.Column("cantidadDisponible", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("supermercado", new TableInfo.Column("supermercado", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("categoria", new TableInfo.Column("categoria", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsProducto.put("esGenerico", new TableInfo.Column("esGenerico", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysProducto = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesProducto = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoProducto = new TableInfo("producto", _columnsProducto, _foreignKeysProducto, _indicesProducto);
        final TableInfo _existingProducto = TableInfo.read(db, "producto");
        if (!_infoProducto.equals(_existingProducto)) {
          return new RoomOpenHelper.ValidationResult(false, "producto(cl.duoc.basico.model.Producto).\n"
                  + " Expected:\n" + _infoProducto + "\n"
                  + " Found:\n" + _existingProducto);
        }
        final HashMap<String, TableInfo.Column> _columnsUsuario = new HashMap<String, TableInfo.Column>(4);
        _columnsUsuario.put("idUsuario", new TableInfo.Column("idUsuario", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUsuario.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsuario = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsuario = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsuario = new TableInfo("usuario", _columnsUsuario, _foreignKeysUsuario, _indicesUsuario);
        final TableInfo _existingUsuario = TableInfo.read(db, "usuario");
        if (!_infoUsuario.equals(_existingUsuario)) {
          return new RoomOpenHelper.ValidationResult(false, "usuario(cl.duoc.basico.model.Usuario).\n"
                  + " Expected:\n" + _infoUsuario + "\n"
                  + " Found:\n" + _existingUsuario);
        }
        final HashMap<String, TableInfo.Column> _columnsFavorito = new HashMap<String, TableInfo.Column>(3);
        _columnsFavorito.put("idFavorito", new TableInfo.Column("idFavorito", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorito.put("usuario", new TableInfo.Column("usuario", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFavorito.put("producto", new TableInfo.Column("producto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFavorito = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFavorito = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFavorito = new TableInfo("favorito", _columnsFavorito, _foreignKeysFavorito, _indicesFavorito);
        final TableInfo _existingFavorito = TableInfo.read(db, "favorito");
        if (!_infoFavorito.equals(_existingFavorito)) {
          return new RoomOpenHelper.ValidationResult(false, "favorito(cl.duoc.basico.model.Favorito).\n"
                  + " Expected:\n" + _infoFavorito + "\n"
                  + " Found:\n" + _existingFavorito);
        }
        final HashMap<String, TableInfo.Column> _columnsSupermercado = new HashMap<String, TableInfo.Column>(7);
        _columnsSupermercado.put("idSupermercado", new TableInfo.Column("idSupermercado", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupermercado.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupermercado.put("latitud", new TableInfo.Column("latitud", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupermercado.put("longitud", new TableInfo.Column("longitud", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupermercado.put("direccion", new TableInfo.Column("direccion", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupermercado.put("comuna", new TableInfo.Column("comuna", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSupermercado.put("region", new TableInfo.Column("region", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSupermercado = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSupermercado = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSupermercado = new TableInfo("supermercado", _columnsSupermercado, _foreignKeysSupermercado, _indicesSupermercado);
        final TableInfo _existingSupermercado = TableInfo.read(db, "supermercado");
        if (!_infoSupermercado.equals(_existingSupermercado)) {
          return new RoomOpenHelper.ValidationResult(false, "supermercado(cl.duoc.basico.model.Supermercado).\n"
                  + " Expected:\n" + _infoSupermercado + "\n"
                  + " Found:\n" + _existingSupermercado);
        }
        final HashMap<String, TableInfo.Column> _columnsRegion = new HashMap<String, TableInfo.Column>(2);
        _columnsRegion.put("idRegion", new TableInfo.Column("idRegion", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRegion.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRegion = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRegion = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRegion = new TableInfo("region", _columnsRegion, _foreignKeysRegion, _indicesRegion);
        final TableInfo _existingRegion = TableInfo.read(db, "region");
        if (!_infoRegion.equals(_existingRegion)) {
          return new RoomOpenHelper.ValidationResult(false, "region(cl.duoc.basico.model.Region).\n"
                  + " Expected:\n" + _infoRegion + "\n"
                  + " Found:\n" + _existingRegion);
        }
        final HashMap<String, TableInfo.Column> _columnsComuna = new HashMap<String, TableInfo.Column>(3);
        _columnsComuna.put("idComuna", new TableInfo.Column("idComuna", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComuna.put("nombre", new TableInfo.Column("nombre", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsComuna.put("region", new TableInfo.Column("region", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysComuna = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesComuna = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoComuna = new TableInfo("comuna", _columnsComuna, _foreignKeysComuna, _indicesComuna);
        final TableInfo _existingComuna = TableInfo.read(db, "comuna");
        if (!_infoComuna.equals(_existingComuna)) {
          return new RoomOpenHelper.ValidationResult(false, "comuna(cl.duoc.basico.model.Comuna).\n"
                  + " Expected:\n" + _infoComuna + "\n"
                  + " Found:\n" + _existingComuna);
        }
        final HashMap<String, TableInfo.Column> _columnsCarrito = new HashMap<String, TableInfo.Column>(6);
        _columnsCarrito.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("productoId", new TableInfo.Column("productoId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("nombreProducto", new TableInfo.Column("nombreProducto", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("precio", new TableInfo.Column("precio", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("cantidad", new TableInfo.Column("cantidad", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCarrito.put("usuario", new TableInfo.Column("usuario", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCarrito = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCarrito = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCarrito = new TableInfo("carrito", _columnsCarrito, _foreignKeysCarrito, _indicesCarrito);
        final TableInfo _existingCarrito = TableInfo.read(db, "carrito");
        if (!_infoCarrito.equals(_existingCarrito)) {
          return new RoomOpenHelper.ValidationResult(false, "carrito(cl.duoc.basico.model.ItemCarrito).\n"
                  + " Expected:\n" + _infoCarrito + "\n"
                  + " Found:\n" + _existingCarrito);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "188e2984d8f81964ae27caee6812bf52", "ef0cfb555b92b461069874ce78e1ee86");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "producto","usuario","favorito","supermercado","region","comuna","carrito");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `producto`");
      _db.execSQL("DELETE FROM `usuario`");
      _db.execSQL("DELETE FROM `favorito`");
      _db.execSQL("DELETE FROM `supermercado`");
      _db.execSQL("DELETE FROM `region`");
      _db.execSQL("DELETE FROM `comuna`");
      _db.execSQL("DELETE FROM `carrito`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ProductoDao.class, ProductoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UsuarioDao.class, UsuarioDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(FavoritoDao.class, FavoritoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SupermercadoDao.class, SupermercadoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(RegionDao.class, RegionDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ComunaDao.class, ComunaDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CarritoDao.class, CarritoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ProductoDao productoDao() {
    if (_productoDao != null) {
      return _productoDao;
    } else {
      synchronized(this) {
        if(_productoDao == null) {
          _productoDao = new ProductoDao_Impl(this);
        }
        return _productoDao;
      }
    }
  }

  @Override
  public UsuarioDao usuarioDao() {
    if (_usuarioDao != null) {
      return _usuarioDao;
    } else {
      synchronized(this) {
        if(_usuarioDao == null) {
          _usuarioDao = new UsuarioDao_Impl(this);
        }
        return _usuarioDao;
      }
    }
  }

  @Override
  public FavoritoDao favoritoDao() {
    if (_favoritoDao != null) {
      return _favoritoDao;
    } else {
      synchronized(this) {
        if(_favoritoDao == null) {
          _favoritoDao = new FavoritoDao_Impl(this);
        }
        return _favoritoDao;
      }
    }
  }

  @Override
  public SupermercadoDao supermercadoDao() {
    if (_supermercadoDao != null) {
      return _supermercadoDao;
    } else {
      synchronized(this) {
        if(_supermercadoDao == null) {
          _supermercadoDao = new SupermercadoDao_Impl(this);
        }
        return _supermercadoDao;
      }
    }
  }

  @Override
  public RegionDao regionDao() {
    if (_regionDao != null) {
      return _regionDao;
    } else {
      synchronized(this) {
        if(_regionDao == null) {
          _regionDao = new RegionDao_Impl(this);
        }
        return _regionDao;
      }
    }
  }

  @Override
  public ComunaDao comunaDao() {
    if (_comunaDao != null) {
      return _comunaDao;
    } else {
      synchronized(this) {
        if(_comunaDao == null) {
          _comunaDao = new ComunaDao_Impl(this);
        }
        return _comunaDao;
      }
    }
  }

  @Override
  public CarritoDao carritoDao() {
    if (_carritoDao != null) {
      return _carritoDao;
    } else {
      synchronized(this) {
        if(_carritoDao == null) {
          _carritoDao = new CarritoDao_Impl(this);
        }
        return _carritoDao;
      }
    }
  }
}
