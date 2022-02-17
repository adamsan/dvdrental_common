### Commands for creating a user with the necessary privileges:

```sql
create user dvd_user with encrypted password '<password>';
grant all privileges on database dvdrental to dvd_user;
-- after connecting to dvdrental database:
grant usage on schema public to dvd_user;
grant all on all tables in schema public to dvd_user;
grant all on all sequences in schema public to dvd user;
```

### Feladat:

A dvdrental adatbázisban fogunk dolgozni.

Írjatok egy általános Dao interface-t, mely tartalmazza a következőket:
- `List<T> findAll();`
- `T findById(int id);`
- `void deleteById(int id);`
- `void save(T entity);`

Készítsetek egy Customer modell osztályt (nem kell a tábla összes oszlopát felvenni),
majd a Dao interfaceből származtassatok le egy `CustomerDao` interface-t,
melyben keresni lehet az email címek alapján is:
 - `List<Customer> findByEmail(String email)`

Készitsetek hozzá implementációt, ami a PgSql adatbázisba ment.
Mi történik a kivételekkel?

Hogyan valósítanátok meg a ResultSet-ből Customer-be alakítást?


A főprogramban futtassatok lekérdezéseket a DAO-n keresztül, írjátok ki az eredményt a konzolra!

A `Connection` létrehozása költséges, ezért csak egy példányt hozzatok létre belőle!
Biztonságosan hoztátok létre a `Connection`-t?
Miután használtátok, bezárjátok rendesen?
Mi történik, ha kivételt dob?

Hogy lehetne elérni, hogy az adatbázis kapcsolat titkos adatai ne kerüljenek bele a forráskódba?
Töltsük be az adatbázis nevet, felhasználó nevet, jelszavat környezeti változóból!

Csináljatok egy `FilmDao`-t is, a `Film` modellben legyen benne az 
- `id`
- `title`
- `description`
- `releaseYear`
- `rating`
- `length`

A `FilmDao`-ban le lehessen kérni filmeket `rating` és `releaseYear` alapján is.