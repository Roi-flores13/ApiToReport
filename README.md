# 🏀 NBA Telegram Bot - Reporte Automatizado

Este proyecto es una aplicación backend en Java que actúa como un *middleware* de integración. Se encarga de consultar diariamente la API de ESPN para obtener los resultados y estadísticas de la jornada de la NBA, procesar y mapear los datos mediante Programación Orientada a Objetos (POO), y enviar un resumen formateado directamente a un chat de Telegram mediante un bot.

El proyecto está diseñado para ejecutarse de forma totalmente autónoma en la nube utilizando **GitHub Actions**.

## ✨ Características Principales

* **Consumo de APIs REST:** Peticiones HTTP eficientes a la API pública de ESPN usando `OkHttp3`.
* **Procesamiento de JSON:** Mapeo profundo de árboles JSON complejos a objetos Java nativos (POJOs) utilizando `Gson`.
* **Notificaciones Push:** Integración nativa con la API de Telegram Bots para enviar mensajes con formato HTML.
* **Automatización en la Nube:** Configurado como un *Cron Job* en GitHub Actions para ejecutarse todos los días de manera desatendida.
* **Gestión de Fecha Dinámica:** Calcula la fecha en tiempo real basándose en la zona horaria `America/Mexico_City`.

## 🏗️ Arquitectura y Principios POO

El sistema fue diseñado aplicando rigorosos principios de diseño de software:
* **Abstracción y Herencia:** Uso de una clase abstracta `EventoDeportivo` de la cual hereda la clase especializada `PartidoNBA`.
* **Composición y Agregación:** Relaciones estructuradas entre `Equipo`, `Estadisticas` y listas de `JugadorLider`.
* **Interfaces:** Uso de la interfaz `Notificador` para estandarizar el envío de mensajes, implementada por `TelegramBotService` (permitiendo escalar a otros medios como Email o WhatsApp en el futuro).
* **Responsabilidad Única (SRP):** Separación estricta entre la recolección de datos (`ApiRequest`), el mapeo (`JsonMapper`), la orquestación (`Main`) y la presentación (`ReporteGenerator`).

### Diagrama de Clases

```mermaid
classDiagram
  class EventoDeportivo {
    <<abstract>>
    #String id
    #String fecha
    #String estado
  }
  class PartidoNBA {
    +String LIGA$
    -Equipo equipoLocal
    -Equipo equipoVisitante
  }
  class Equipo {
    -Estadisticas estadisticas
    -List~JugadorLider~ lideres
  }
  class Notificador {
    <<interface>>
    +enviarMensaje(dest: String, msg: String) boolean
  }
  class TelegramBotService {
    +enviarMensaje(chatId: String, msg: String) boolean
  }
  
  EventoDeportivo <|-- PartidoNBA
  PartidoNBA o-- Equipo
  Notificador <|.. TelegramBotService
