# MobileQueries
 ### Used Techs
- Android Studio - Java
- Google Cloud
  - IAM
  - BigQuery
- Mapbox
  - Navigation Service
  - Maps Service

---

### Query Data

Database data is acquired from The New York City Taxi and Limousine Commision (TLC) and stored in BigQuery database. December 2020 data is used in this project.
- [yellow_tripdata_2020-12.csv](https://www1.nyc.gov/site/tlc/about/tlc-trip-record-data.page).
- [taxi+_zone_lookup.csv](https://www1.nyc.gov/site/tlc/about/tlc-trip-record-data.page).

---

### Structure Diagram

![Schema](https://user-images.githubusercontent.com/41192900/157479835-9b9b40e7-2ab2-4254-803f-5530ac709c0a.PNG)

---
### Type 1 Query

This Query is returns "The five most passenger transported day and transported passenger values." from BigQuery database.

![Ekran Alıntısı](https://user-images.githubusercontent.com/41192900/157481803-51e43625-dd77-4289-aa0f-1ad0a81277e5.PNG)

---

### Type 2 Query

This query returns "The five shortest distanced transportation records between two input dates." from BigQuery database.

![image](https://user-images.githubusercontent.com/41192900/157483556-a0a7844a-a878-45f6-99a1-444eefa46713.png)

---

### Type 3 Query

This Query returns "The longest distanced transportation at input date." from BigQuery database and shows the transportation road in mapbox map.

![image](https://user-images.githubusercontent.com/41192900/157484496-520d7130-4f6c-42f3-adc8-cb9bd2b8c03a.png)
![image](https://user-images.githubusercontent.com/41192900/157484714-77eaf5cf-6129-4a79-aec8-8195fa690fd9.png)

