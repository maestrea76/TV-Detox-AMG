# Historial de cambios

Cambios destacados de TV Detox AMG. El proyecto es independiente desde el 22 de
octubre de 2026: las versiones hasta la 1.5.1 proceden del código de TV Detox del
que partió, y a partir de ahora este repositorio fija sus propias versiones.

## 1.6.1 — 24 de octubre de 2026

- La píldora de entradas vuelve a llamarse **Entradas** y su menú lista todas las entradas HDMI,
  como los mosaicos a los que sustituyó. En la pantalla de inicio no hay una entrada activa, así que
  la píldora ya no afirma tenerla: en la 1.6.0 tomaba el nombre de la primera entrada (o de la
  última elegida) y podía decir `Fire TV` mientras el televisor mostraba otra HDMI (`MainActivity`).
- La píldora de Ajustes queda justo a la izquierda del icono de red, y la de Entradas a la izquierda
  de Ajustes, el mismo orden que tenían los mosaicos antiguos (`MainActivity`).
- Sin la fila de utilidades, las aplicaciones bajan al espacio que esa fila dejó libre en vez de
  quedarse una fila más arriba de lo necesario (`MainActivity`).
- La sombra del foco es más grande y más clara, y el borde del mosaico enfocado pasa a blanco puro,
  para distinguir la selección desde el sofá (`MainActivity`).

## 1.6.0 — 24 de octubre de 2026

- La fila de utilidades bajo las aplicaciones desaparece. Ajustes pasa a ser una píldora en la línea
  del reloj, a la izquierda del icono de red, y las entradas HDMI del televisor son otra píldora a su
  lado. La píldora de entradas toma el nombre de la entrada elegida en su menú (`HDMI 1`, `Dormitorio`…),
  así que la selección y la entrada ocupan una línea de texto en vez de una fila de mosaicos. Eso
  libera una fila entera de espacio para las aplicaciones (`MainActivity`).
- El menú de la píldora de Ajustes incluye ahora **Añadir app** y **Google Play** junto a Fondo,
  Tiempo e Idioma, así que instalar más aplicaciones ya no necesita mosaicos propios.
- El mosaico, icono o píldora con el foco recibe una sombra difuminada con su propia forma, para
  localizar la selección de un vistazo sobre el fondo (`MainActivity`).
- `install.sh` ahora descarga el APK de respaldo de la última release de este repositorio en vez de
  la del TV Detox original, así que instalar sin compilar ya no trae la aplicación del proyecto
  upstream.

## 1.5.2 — 22 de octubre de 2026

- Tiempo en modo Extra grande: con dos filas de aplicaciones en la pantalla de inicio,
  la tira de siete días muestra cada día en una sola línea (día, cielo, máx/mín) en vez
  de tres, y la rejilla de apps empieza bajo la altura real de la cabecera. Con eso las
  tarjetas dejan de tapar las temperaturas del pronóstico en televisores de 1080p
  (`MainActivity`).

## 1.5.1 — línea independiente (versionName 1.5.1, versionCode 14)

Pantalla de inicio, todo en `src/com/ansub/tvhome/`:

- Superficies y estados de foco con efecto glass en las tarjetas de aplicaciones,
  sistema y HDMI (`MainActivity`, `89af3a5`).
- Nueva cabecera con reloj grande, fecha localizada, saludo y línea del tiempo,
  además de una línea del tiempo meteorológico de Open-Meteo que se actualiza
  cada 30 minutos (`MainActivity`).
- El tiempo tiene cuatro modos — Pequeño (icono y temperatura), Compacto (añade
  la ciudad), Grande (estado del cielo, sensación térmica, humedad y viento) y
  Extra grande (añade la máxima/mínima de hoy y una fila de siete días con el
  cielo, la máxima y la mínima de cada día) — y un buscador de ciudad que guarda
  la elección. El formulario queda arriba para que el teclado de la TV no lo
  tape. Ubicación por defecto: Getaria, Gipuzkoa (`f8593d9`, `3d9faf5`).
- Tarjeta de reproducción basada en un listener de notificaciones: muestra el
  contenido activo y vuelve al reproductor al seleccionarla. Añade
  `NowPlaying.java`, `MediaListener.java`, el servicio de notificaciones y sus
  entradas en el manifest; `install.sh` concede el acceso cuando puede
  (`37f3ae4`).
- El menú de Ajustes ofrece Fondo, Tiempo, Idioma y Ajustes de la TV.
- El ajuste de Idioma cambia las etiquetas entre español e inglés; sin nada
  guardado, siguen el idioma del televisor (`a762034`, `Strings.java`).
- README en español y documentación actualizada (`ea8fbb7`, `aeb4082`).

## 1.5.1 — 20/10/2026 (versionCode 14)

- Los televisores OLED conservan el salvapantallas: el asistente de instalación
  ya no lo desactiva en todos los televisores, y los salvapantallas de Android y
  de Google TV se marcan como **keep**.
- Si falta una traducción, se muestra en inglés en vez de cerrar la pantalla de
  inicio.

## 1.5.0 — 20/10/2026 (versionCode 13)

- 14 fondos degradados, 7 oscuros y 7 claros, elegidos desde el menú del tile de
  Ajustes. Al recorrer la lista se previsualiza cada uno; OK lo guarda y Atrás
  recupera el anterior. Solo colores: sin imágenes, permisos ni memoria extra.
- El tile de Ajustes abre un menú (Fondo, Ajustes de la TV).
- Corrección: una app solo se abre si el OK se pulsa sobre su tile, así el
  "Exit" de Netflix ya no reabre Netflix.
- Corrección: si la conexión se pierde durante la instalación, se avisa de eso en
  vez de culpar a la pantalla de inicio.
- Corrección: `check.sh` acepta la pantalla de inicio de Google TV cuando
  `install.sh` la dejó activa.
- Corrección: `check.sh` y `undo.sh` manejan la lista de la instalación con Git
  Bash en Windows.

## 1.4.2 — 20/10/2026 (versionCode 12)

- Televisores Philips: las tarjetas HDMI muestran el nombre de la entrada sin el
  sufijo `&TIPO_DE_DISPOSITIVO`.

## 1.4.1 — 20/10/2026 (versionCode 11)

- Cambio de seguridad para Google TV y Chromecast: `install.sh` deja activa la
  pantalla de inicio de Google TV salvo que se use `GOOGLE_TV=1`. La limpieza
  sigue funcionando; solo se omite el cambio de launcher.

## 1.4.0 — 20/10/2026 (versionCode 10)

- Nuevo `candidates.sh`: clasifica las apps instaladas en never / keep / safe /
  review sin cambiar nada, junto a `docs/debloat.md` con las reglas.
- Red de seguridad en Google TV: si no se puede sustituir el launcher original,
  `install.sh` lo vuelve a activar en vez de dejar la TV sin pantalla de inicio.
- Al desinstalar una app desde el menú, su tile desaparece al momento y el foco
  pasa a la siguiente.
- `check.sh` se detiene y lo explica si no puede leer la lista de apps.
- El CI revisa todos los scripts con ShellCheck; las releases esperan a que pase.
- Notas para TCL Google TV en el README.

## 1.3.0 — 19/10/2026 (versionCode 9)

- Las etiquetas y la fecha siguen el idioma del televisor: 16 idiomas (árabe,
  chino simplificado y tradicional, neerlandés, inglés, francés, alemán, hindi,
  indonesio, italiano, japonés, coreano, polaco, portugués de Brasil, ruso,
  español y turco).
- Corrección: en algunos Google TV (por ejemplo Sony BRAVIA) el instalador podía
  abrir el asistente de configuración de Google TV en vez de la pantalla de
  inicio; ahora lo detecta y ofrece desactivarlo, con recuperación en `undo.sh`.
- El chino elige simplificado o tradicional por la escritura de la TV y luego por
  la región; la pantalla se actualiza aunque solo cambie la región.

## 1.2.0 — 19/10/2026 (versionCode 8)

- Icono de red junto al reloj: Wi-Fi con barras de señal, Ethernet u offline, en
  vivo; al seleccionarlo se abren los ajustes de red.
- Atajos opcionales de Bluetooth, Sonido e Imagen junto al reloj.
- Entradas Desinstalar e Información de la app en el menú de OK mantenido.
- Nuevo `check.sh` para comprobar la instalación y `--fix` para repararla tras
  una actualización de la TV.
- `install.sh` más seguro: solo desactiva launchers conocidos y espera 15
  segundos tras desactivar uno.
- Corrección: las tarjetas HDMI funcionan en Google TV que entregan la señal al
  reproductor de entradas de Google.

## 1.1.0 — 19/10/2026 (versionCode 7)

- Mantener OK (o Menú) sobre una app abre Mover, Reemplazar y Quitar de inicio;
  la app sigue instalada.
- Nuevo tile Añadir app para elegir cualquier app instalada; los cambios se
  guardan en la TV.
- Las apps se agrupan en filas de seis, hasta doce en pantalla, con scroll
  vertical y navegación en las cuatro direcciones.
- Corrección: tarjetas HDMI en televisores que incluyen Live TV con otro nombre
  (Amlogic, Droidlogic, algunos proyectores Epson).

## 1.0.1 — 18/10/2026 (versionCode 6)

- Primera release pública.
