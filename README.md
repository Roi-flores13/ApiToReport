# 🏀 ESPN NBA Scoreboard API (Unofficial)

Documentación técnica para el consumo del endpoint público de ESPN para resultados y estadísticas en tiempo real de la NBA.

---

## 🚀 Endpoint Principal

| Método | URL |
| :--- | :--- |
| `GET` | `http://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard` |

---

## 🛠 Parámetros de Consulta (Query Params)

Puedes personalizar la respuesta agregando los siguientes parámetros a la URL:

| Parámetro | Tipo | Ejemplo | Descripción |
| :--- | :--- | :--- | :--- |
| `dates` | `string` | `?dates=20240225` | Obtiene los juegos de una fecha específica (Formato: YYYYMMDD). |
| `limit` | `number` | `?limit=50` | Define el número máximo de eventos a retornar. |
| `groups` | `number` | `?groups=7` | Filtra por Conferencia (7: Este, 8: Oeste). |

---

## 📊 Estructura de la Respuesta (JSON)

La respuesta es un objeto JSON profundo. Los puntos clave para mapear los datos son:

### 1. Información de la Liga
Contiene el nombre de la liga, logos y temporada actual.
- **Ruta:** `leagues[0]`

### 2. Listado de Partidos (Events)
Cada objeto dentro del array `events` representa un partido.
- **Ruta:** `events[]`



### 3. Detalles de cada Partido
Dentro de cada objeto en `events`, puedes encontrar:
* **Estado del juego:** `status.type.detail` (Ej: "Final", "12:00 - 1st").
* **Reloj:** `status.displayClock` (Tiempo restante).
* **Equipos y Marcadores:** Se encuentran en `competitions[0].competitors[]`.
    * **Local:** `competitors[0]` (Usualmente).
    * **Visitante:** `competitors[1]` (Usualmente).
    * **Puntaje:** `competitor.score`.
    * **Nombre del equipo:** `competitor.team.displayName`.
    * **Logo:** `competitor.team.logo`.

---

## 💡 Ejemplos Rápidos

### Ver partidos de Navidad 2023
```http
GET [http://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=20231225](http://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard?dates=20231225)