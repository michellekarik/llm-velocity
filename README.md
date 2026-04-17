# llm-velocity


A high-performance Java Spring Boot and Python data pipeline that captures raw GitHub event streams, processes them using a Medallion Data Architecture, and extracts real-time insights into LLM adoption trends (GPT-4, Llama 3, Claude 4) across global developer activity.

Medallion Architecture: Implemented a two-tier data strategy.

Bronze Layer (Raw): High-volume ingestion of raw JSON event data into PostgreSQL via Python.

Silver Layer (Refined): Cleaned, categorized "Mentions" extracted via Java Spring Boot logic.

Automated ETL Pipeline: Engineered a Scheduled Service in Spring Boot that polls the database every 10 seconds to process "Dirty" records into structured insights.

Spring Data JPA: Leveraged Repository Patterns to abstract database complexity and ensure type-safe data persistence.

Keyword Intelligence: Built a prioritized classification engine to detect specific 2026 frontier models (o3-mini, Llama 4, DeepSeek-V3) from unstructured text payloads.

<img width="1916" height="858" alt="image" src="https://github.com/user-attachments/assets/faa15106-b53a-4a3d-8b77-16e4d883fd99" />
