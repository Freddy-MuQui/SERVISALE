# SERVISALE
📋 Descripción:
SERVISALE es una aplicación móvil innovadora dedicada al servicio de búsqueda y comparación de productos en tiendas físicas. La aplicación permite a los usuarios encontrar rápidamente productos de diversas categorías (alimentos, ropa, accesorios, etc.) en supermercados y tiendas de su zona, comparando precios y disponibilidad en tiempo real.

🎯 Objetivo Principal
Facilitar la búsqueda de productos ofreciendo información detallada sobre:

Precio: Encuentra la tienda con el mejor precio

Disponibilidad: Conoce el stock disponible

Ubicación: Localiza la tienda más cercana con mapas interactivos

Cobertura: Búsqueda por comuna, región o a nivel nacional

La aplicación se enfoca principalmente en supermercados y tiendas de marcas reconocidas, proporcionando una experiencia de usuario fluida y eficiente para la toma de decisiones de compra informadas.

👥 Equipo de Desarrollo
Nombre	Rol
Freddy Muñoz	Desarrollador
Vicente Maulen	Desarrollador
Asignatura: DSY1105 - Desarrollo de Aplicaciones Móviles
Institución: Instituto Profesional AIEP
Período: 2025-1

✨ Funcionalidades Implementadas
🔍 Búsqueda de Productos
Motor de búsqueda inteligente para encontrar productos por nombre o categoría

Filtros avanzados por tipo de producto (alimentos, ropa, accesorios, etc.)

Búsqueda geolocalizada según ubicación del usuario

💰 Comparación de Precios
Visualización de precios en diferentes tiendas y supermercados

Identificación automática de la opción más económica

Historial de precios (si aplica)

📍 Localización y Mapas
Integración con mapas para mostrar ubicación de tiendas

Direcciones escritas detalladas bajo el mapa

Cálculo de distancia desde ubicación actual del usuario

🌎 Cobertura Geográfica
Búsqueda por comuna específica

Expansión a múltiples comunas

Búsqueda a nivel regional

Cobertura nacional

📦 Información de Disponibilidad
Stock disponible en cada tienda

Indicador visual de disponibilidad

Alertas de productos con bajo stock

🎨 Interfaz y Experiencia de Usuario
Diseño moderno y responsive

Navegación intuitiva entre vistas

Formularios validados con retroalimentación visual

Animaciones fluidas para mejorar la experiencia

💾 Persistencia de Datos
Almacenamiento local de búsquedas recientes

Favoritos y listas de compras

Caché de datos para acceso offline

📱 Recursos Nativos
Acceso a GPS para geolocalización

Integración con mapas nativos

Notificaciones push (opcional)

Cámara para escaneo de códigos de barras (si aplica)

🏗️ Arquitectura del Proyecto
El proyecto sigue el patrón arquitectónico MVVM (Model-View-ViewModel) para garantizar:

Separación de responsabilidades

Código mantenible y escalable

Facilidad de testing

Desacoplamiento entre lógica y UI

Estructura de Carpetas
text
app/
├── src/
│   ├── main/
│   │   ├── java/com/servisale/
│   │   │   ├── data/           # Modelos de datos y repositorios
│   │   │   ├── ui/             # Activities, Fragments y Views
│   │   │   ├── viewmodel/      # ViewModels
│   │   │   ├── utils/          # Utilidades y helpers
│   │   │   └── adapters/       # Adaptadores para RecyclerViews
│   │   ├── res/                # Recursos (layouts, drawables, etc.)
│   │   └── AndroidManifest.xml
│   └── test/                   # Tests unitarios
├── build.gradle                # Configuración de Gradle del módulo
└── proguard-rules.pro
🚀 Pasos para Ejecutar el Proyecto
Requisitos Previos
Antes de comenzar, asegúrate de tener instalado:

Android Studio (versión recomendada: Hedgehog 2023.1.1 o superior)

Descarga desde: https://developer.android.com/studio

JDK (Java Development Kit) versión 11 o superior

SDK de Android:

Android SDK Platform 33 o superior

Android Build Tools 33.0.0 o superior

Dispositivo de prueba:

Emulador Android (configurado en Android Studio)

O dispositivo físico Android con versión 8.0 (API 26) o superior

Instrucciones de Instalación
1. Clonar el Repositorio
bash
git clone https://github.com/[usuario]/servisale.git
cd servisale
2. Abrir el Proyecto en Android Studio
Abre Android Studio

Selecciona File > Open

Navega hasta la carpeta del proyecto clonado

Haz clic en OK

Espera a que Gradle sincronice las dependencias (puede tomar varios minutos la primera vez)

3. Configurar el Emulador (Opcional)
Si no tienes un dispositivo físico:

Ve a Tools > Device Manager

Haz clic en Create Device

Selecciona un dispositivo (recomendado: Pixel 6)

Selecciona una imagen del sistema (API 33 o superior)

Finaliza la configuración

4. Configurar API Keys (Si aplica)
Si el proyecto utiliza servicios externos (Google Maps, APIs de productos), configura las claves:

Crea un archivo local.properties en la raíz del proyecto (si no existe)

Agrega tus API keys:

text
MAPS_API_KEY=tu_api_key_aqui
PRODUCTS_API_KEY=tu_api_key_aqui
5. Compilar y Ejecutar
Opción A: Desde Android Studio

Conecta tu dispositivo físico o inicia el emulador

Haz clic en el botón Run (▶️) o presiona Shift + F10

Selecciona el dispositivo de destino

Espera a que la aplicación se compile e instale

Opción B: Desde la Terminal

bash
# Compilar el proyecto
./gradlew assembleDebug

# Instalar en dispositivo conectado
./gradlew installDebug

# O ejecutar directamente
./gradlew run
6. Verificar Permisos
La aplicación solicitará permisos en tiempo de ejecución para:

📍 Ubicación: Necesario para mostrar tiendas cercanas

📷 Cámara: Para escaneo de códigos de barras (opcional)

Acepta los permisos cuando se soliciten para un funcionamiento completo.

🔧 Configuración Adicional
Dependencias Principales
El proyecto utiliza las siguientes bibliotecas:

text
// ViewModel y LiveData
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.6.2'

// Room para persistencia local
implementation 'androidx.room:room-runtime:2.6.0'
kapt 'androidx.room:room-compiler:2.6.0'

// Google Maps
implementation 'com.google.android.gms:play-services-maps:18.2.0'
implementation 'com.google.android.gms:play-services-location:21.0.1'

// Retrofit para APIs
implementation 'com.squareup.retrofit2:retrofit:2.9.0'
implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

// Material Design
implementation 'com.google.android.material:material:1.10.0'
Variables de Entorno
Para un funcionamiento completo, configura las siguientes variables en local.properties:

text
# API de Google Maps
MAPS_API_KEY=AIza...

# API de productos (si es personalizada)
API_BASE_URL=https://api.servisale.cl/v1/

# Firebase (si se utiliza)
FIREBASE_PROJECT_ID=servisale-app
🧪 Testing
Ejecutar Tests Unitarios
bash
./gradlew test
Ejecutar Tests de Instrumentación
bash
./gradlew connectedAndroidTest
📱 Capturas de Pantalla
Nota: Agregar capturas de pantalla de las principales vistas de la aplicación:

Pantalla de búsqueda

Resultados de búsqueda con precios

Vista de mapa con ubicación de tiendas

Detalle de producto

Comparación de precios

🛠️ Tecnologías Utilizadas
Tecnología	Descripción
Kotlin	Lenguaje de programación principal
Android SDK	Framework de desarrollo Android
MVVM	Patrón arquitectónico
Room	Base de datos local
Retrofit	Cliente HTTP para APIs REST
Google Maps API	Integración de mapas
Material Design	Componentes de UI
LiveData	Observación de datos reactiva
Coroutines	Programación asíncrona
📊 Gestión del Proyecto
Control de Versiones
GitHub: Repositorio principal con commits descriptivos y distribuidos

Ramas: Desarrollo basado en feature branches

Planificación
Trello: Tablero Kanban para gestión de tareas

Issues: Tracking de bugs y mejoras en GitHub

🔄 Estado del Proyecto
Este proyecto corresponde a la Evaluación Parcial 2 de la asignatura Desarrollo de Aplicaciones Móviles.

Estado Actual: ✅ En desarrollo - Versión funcional básica implementada

Próximas Mejoras
 Integración con APIs reales de supermercados

 Sistema de notificaciones de ofertas

 Comparador de listas de compras

 Historial de precios con gráficos

 Sistema de valoraciones de tiendas

 Modo offline mejorado

📄 Licencia
Este proyecto es desarrollado con fines educativos para la asignatura DSY1105 del Instituto Profesional DUOC UC San Joaquin.

📞 Contacto
Para consultas sobre el proyecto:

Freddy Muñoz: [correo/github]

Vicente Maulen: [correo/github]

🙏 Agradecimientos
Instituto Profesional DUOC UC San Joaquin

Docente de la asignatura DSY1105

Comunidad de desarrolladores Android
