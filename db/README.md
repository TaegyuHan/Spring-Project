# MySQL 예제 데이터 베이스 (sakila)

MySQL의 예제 데이터베이스인 sakila는 DVD 대여 상점과 관련된 데이터를 모델링한 것입니다. 이 데이터베이스는 DVD 대여 서비스의 일반적인 비즈니스 요구 사항을 반영하며, 실제 업무 환경과 유사한 데이터를 사용하여 MySQL 데이터베이스를 연습하고 학습하는 데 유용합니다.

sakila 데이터베이스는 다음과 같은 테이블을 포함합니다.

[[DB Schema 보기]](https://drive.google.com/file/d/17WvRDoheIKtEZUAYl7qYIBKp-Gv37J7I/view?usp=sharing)

---

# `actor`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`actor`** 테이블은 영화 배우(actor) 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE actor (
  actor_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (actor_id),
  KEY idx_actor_last_name (last_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`actor_id`** : 영화 배우의 ID 정보를 저장하는 컬럼
- **`first_name`** : 영화 배우의 이름(이름) 정보를 저장하는 컬럼
- **`last_nama`** : 영화 배우의 성(성씨) 정보를 저장하는 컬럼
- **`last_update`** : 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

## `actor_info`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 뷰는 배우(actor)들의 정보와 배우들이 출연한 영화(film)들의 정보를 제공합니다.

```sql
CREATE DEFINER=CURRENT_USER SQL SECURITY INVOKER VIEW actor_info
AS
SELECT
a.actor_id,
a.first_name,
a.last_name,
GROUP_CONCAT(DISTINCT CONCAT(c.name, ': ',
		(SELECT GROUP_CONCAT(f.title ORDER BY f.title SEPARATOR ', ')
                    FROM sakila.film f
                    INNER JOIN sakila.film_category fc
                      ON f.film_id = fc.film_id
                    INNER JOIN sakila.film_actor fa
                      ON f.film_id = fa.film_id
                    WHERE fc.category_id = c.category_id
                    AND fa.actor_id = a.actor_id
                 )
             )
             ORDER BY c.name SEPARATOR '; ')
AS film_info
FROM sakila.actor a
LEFT JOIN sakila.film_actor fa
  ON a.actor_id = fa.actor_id
LEFT JOIN sakila.film_category fc
  ON fa.film_id = fc.film_id
LEFT JOIN sakila.category c
  ON fc.category_id = c.category_id
GROUP BY a.actor_id, a.first_name, a.last_name;
```

---

# **`address`**

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`address`** 테이블은 고객(customer)의 주소 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE address (
  address_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  address VARCHAR(50) NOT NULL,
  address2 VARCHAR(50) DEFAULT NULL,
  district VARCHAR(20) NOT NULL,
  city_id SMALLINT UNSIGNED NOT NULL,
  postal_code VARCHAR(10) DEFAULT NULL,
  phone VARCHAR(20) NOT NULL,
  -- Add GEOMETRY column for MySQL 5.7.5 and higher
  -- Also include SRID attribute for MySQL 8.0.3 and higher
  /*!50705 location GEOMETRY */ /*!80003 SRID 0 */ /*!50705 NOT NULL,*/
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (address_id),
  KEY idx_fk_city_id (city_id),
  /*!50705 SPATIAL KEY `idx_location` (location),*/
  CONSTRAINT `fk_address_city` FOREIGN KEY (city_id) REFERENCES city (city_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`address_id`**: 주소 ID를 저장하는 컬럼
- **`address`**: 주소의 첫 번째 라인을 저장하는 컬럼
- **`address2`**: 주소의 두 번째 라인을 저장하는 컬럼
- **`district`**: 주소가 속한 구역을 저장하는 컬럼
- **`city_id`**: 해당 주소가 속한 도시(city)의 ID 정보를 저장하는 컬럼
- **`postal_code`**: 우편번호 정보를 저장하는 컬럼
- **`phone`**: 주소와 연관된 전화번호 정보를 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# **`category`**

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`category`** 테이블은 영화 카테고리(category) 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE category (
  category_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name VARCHAR(25) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`category_id`**: 카테고리를 구분하는 유일한 ID 정보를 저장하는 컬럼
- **`name`**: 카테고리의 이름을 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# **`city`**

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`city`** 테이블은 도시(city) 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE city (
  city_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  city VARCHAR(50) NOT NULL,
  country_id SMALLINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (city_id),
  KEY idx_fk_country_id (country_id),
  CONSTRAINT `fk_city_country` FOREIGN KEY (country_id) REFERENCES country (country_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`city_id`**: 도시 ID를 저장하는 컬럼
- **`city`**: 도시 이름 정보를 저장하는 컬럼
- **`country_id`**: 해당 도시가 속한 국가(country)의 ID 정보를 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# **`country`**

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`country`** 테이블은 국가(country) 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE country (
  country_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  country VARCHAR(50) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (country_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`country_id`**: 국가 ID를 저장하는 컬럼
- **`country`**: 국가 이름 정보를 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# `customer`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`customer`** 테이블은 영화 대여점의 고객 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE customer (
  customer_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  store_id TINYINT UNSIGNED NOT NULL,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  email VARCHAR(50) DEFAULT NULL,
  address_id SMALLINT UNSIGNED NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  create_date DATETIME NOT NULL,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (customer_id),
  KEY idx_fk_store_id (store_id),
  KEY idx_fk_address_id (address_id),
  KEY idx_last_name (last_name),
  CONSTRAINT fk_customer_address FOREIGN KEY (address_id) REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_customer_store FOREIGN KEY (store_id) REFERENCES store (store_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`customer_id`**: 고객 ID를 저장하는 컬럼
- **`store_id`**: 해당 고객이 속한 가게(store)의 ID 정보를 저장하는 컬럼
- **`first_name`** : 영화 배우의 이름(이름) 정보를 저장하는 컬럼
- **`last_nama`** : 영화 배우의 성(성씨) 정보를 저장하는 컬럼
- **`email`**: 고객의 이메일 주소 정보를 저장하는 컬럼
- **`address_id`**: 해당 고객의 주소 정보를 저장하는 컬럼
- **`active`**: 해당 고객이 활성화되어 있는지 여부를 저장하는 컬럼
- **`create_date`**: 해당 고객 레코드가 생성된 시간을 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

## `customer_list`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

`customer` 테이블과 `address`, `city`, `country` 테이블을 조인하여, 고객의 이름, 주소, 우편번호, 전화번호, 도시, 국가 등의 정보를 담고 있는 뷰입니다.

`customer_list` 뷰를 사용하면, 고객 정보를 쉽게 조회할 수 있습니다. 이 뷰를 사용하여, 고객 정보를 기반으로 한 다양한 보고서나 통계 등을 생성할 수 있습니다.

```sql
CREATE VIEW customer_list
AS
SELECT cu.customer_id AS ID, CONCAT(cu.first_name, _utf8mb4' ', cu.last_name) AS name, a.address AS address, a.postal_code AS `zip code`,
	a.phone AS phone, city.city AS city, country.country AS country, IF(cu.active, _utf8mb4'active',_utf8mb4'') AS notes, cu.store_id AS SID
FROM customer AS cu JOIN address AS a ON cu.address_id = a.address_id JOIN city ON a.city_id = city.city_id
	JOIN country ON city.country_id = country.country_id;
```

---

# `film`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`film`** 테이블은 영화 정보를 저장하는 테이블 입니다.

```sql
CREATE TABLE film (
  film_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  title VARCHAR(128) NOT NULL,
  description TEXT DEFAULT NULL,
  release_year YEAR DEFAULT NULL,
  language_id TINYINT UNSIGNED NOT NULL,
  original_language_id TINYINT UNSIGNED DEFAULT NULL,
  rental_duration TINYINT UNSIGNED NOT NULL DEFAULT 3,
  rental_rate DECIMAL(4,2) NOT NULL DEFAULT 4.99,
  length SMALLINT UNSIGNED DEFAULT NULL,
  replacement_cost DECIMAL(5,2) NOT NULL DEFAULT 19.99,
  rating ENUM('G','PG','PG-13','R','NC-17') DEFAULT 'G',
  special_features SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (film_id),
  KEY idx_title (title),
  KEY idx_fk_language_id (language_id),
  KEY idx_fk_original_language_id (original_language_id),
  CONSTRAINT fk_film_language FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_film_language_original FOREIGN KEY (original_language_id) REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`film_id`**: 영화 ID를 저장하는 컬럼
- **`title`**: 영화 제목을 저장하는 컬럼
- **`description`**: 영화 설명을 저장하는 컬럼
- **`release_year`**: 영화 개봉 연도를 저장하는 컬럼
- **`language_id`**: 해당 영화의 언어 정보를 저장하는 language 컬럼
- **`original_language_id`**: 해당 영화의 원래 언어 정보를 저장하는 컬럼
- **`rental_duration`**: 해당 영화의 대여 기간을 일(day) 단위로 저장하는 컬럼
- **`rental_rate`**: 해당 영화의 대여 비용을 소수점 2자리까지 저장하는 컬럼
- **`length`**: 해당 영화의 상영 시간을 분(minute) 단위로 저장하는 컬럼
- **`replacement_cost`**: 해당 영화를 대여할 경우 대여료 이외에 추가로 내야하는 비용을 소수점 2자리까지 저장하는 컬럼
- **`rating`**: 해당 영화의 등급 정보를 저장하는 컬럼
- **`special_features`**: 해당 영화의 추가적인 특징 정보를 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

## `film_list`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

영화 정보와 관련된 데이터를 조인하여 조회하는 역할을 합니다.

```sql
CREATE VIEW film_list
AS
SELECT film.film_id AS FID, film.title AS title, film.description AS description, category.name AS category, film.rental_rate AS price,
	film.length AS length, film.rating AS rating, GROUP_CONCAT(CONCAT(actor.first_name, _utf8mb4' ', actor.last_name) SEPARATOR ', ') AS actors
FROM film LEFT JOIN film_category ON film_category.film_id = film.film_id
LEFT JOIN category ON category.category_id = film_category.category_id LEFT
JOIN film_actor ON film.film_id = film_actor.film_id LEFT JOIN actor ON
  film_actor.actor_id = actor.actor_id
GROUP BY film.film_id, category.name;
```

## `nicer_but_slower_film_list`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

**`nicer_but_slower_film_list`**뷰는 **`film_list`** 뷰와 거의 비슷하지만, 액터 이름을 첫 글자만 대문자로 표기하고 나머지는 소문자로 표기하는 부분이 다릅니다.

```sql
CREATE VIEW nicer_but_slower_film_list
AS
SELECT film.film_id AS FID, film.title AS title, film.description AS description, category.name AS category, film.rental_rate AS price,
	film.length AS length, film.rating AS rating, GROUP_CONCAT(CONCAT(CONCAT(UCASE(SUBSTR(actor.first_name,1,1)),
	LCASE(SUBSTR(actor.first_name,2,LENGTH(actor.first_name))),_utf8mb4' ',CONCAT(UCASE(SUBSTR(actor.last_name,1,1)),
	LCASE(SUBSTR(actor.last_name,2,LENGTH(actor.last_name)))))) SEPARATOR ', ') AS actors
FROM film LEFT JOIN film_category ON film_category.film_id = film.film_id
LEFT JOIN category ON category.category_id = film_category.category_id LEFT
JOIN film_actor ON film.film_id = film_actor.film_id LEFT JOIN actor ON
  film_actor.actor_id = actor.actor_id
GROUP BY film.film_id, category.name;
```

---

# `film_actor`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 영화(film)와 배우(actor) 테이블 간의 다대다(N:M) 관계를 나타내기 위한 연결 테이블입니다.

```sql
CREATE TABLE film_actor (
  actor_id SMALLINT UNSIGNED NOT NULL,
  film_id SMALLINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (actor_id,film_id),
  KEY idx_fk_film_id (`film_id`),
  CONSTRAINT fk_film_actor_actor FOREIGN KEY (actor_id) REFERENCES actor (actor_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_film_actor_film FOREIGN KEY (film_id) REFERENCES film (film_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`actor_id`**: 해당 레코드에 대응하는 배우(actor)의 식별자를 저장하는 컬럼
- **`film_id`**: 해당 레코드에 대응하는 영화(film)의 식별자를 저장하는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# `film_category`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 영화(film)와 카테고리(category) 테이블 간의 다대다(N:M) 관계를 나타내기 위한 연결 테이블입니다.

```sql
CREATE TABLE film_category (
  film_id SMALLINT UNSIGNED NOT NULL,
  category_id TINYINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (film_id, category_id),
  CONSTRAINT fk_film_category_film FOREIGN KEY (film_id) REFERENCES film (film_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_film_category_category FOREIGN KEY (category_id) REFERENCES category (category_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- `film_id` : 영화(film)의 고유 식별자를 나타냅니다.
- `category_id` : 영화 카테고리(category)의 고유 식별자를 나타냅니다.
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# `film_text`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 영화(film) 정보를 저장하는 테이블인 film 테이블과는 별개로, 영화 제목과 설명(description) 정보를 풀텍스트 검색(fulltext search)을 지원하기 위해 별도로 저장하는 테이블입니다.

```sql
-- Use InnoDB for film_text as of 5.6.10, MyISAM prior to 5.6.10.
SET @old_default_storage_engine = @@default_storage_engine;
SET @@default_storage_engine = 'MyISAM';
/*!50610 SET @@default_storage_engine = 'InnoDB'*/;

CREATE TABLE film_text (
  film_id SMALLINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  PRIMARY KEY  (film_id),
  FULLTEXT KEY idx_title_description (title,description)
) DEFAULT CHARSET=utf8mb4;

SET @@default_storage_engine = @old_default_storage_engine;
```

- `film_id`: 영화의 고유한 식별자를 나타내는 컬럼입니다. 기본 키로 사용됩니다.
- `title`: 영화의 제목을 나타내는 컬럼입니다.
- `description`: 영화의 설명을 나타내는 컬럼입니다.

## `ins_film`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 트리거는 film 테이블에 새로운 레코드가 삽입될 때마다 실행됩니다.

```sql
DELIMITER ;;
CREATE TRIGGER `ins_film` AFTER INSERT ON `film` FOR EACH ROW BEGIN
    INSERT INTO film_text (film_id, title, description)
        VALUES (new.film_id, new.title, new.description);
  END;;
DELIMITER ;
```

## `upd_film`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

위 트리거는 **`film`** 테이블에서 **`UPDATE`** 문이 실행되면 실행됩니다. 해당 **`UPDATE`** 문의 각 열에 대한 이전(old) 및 새로운(new) 값을 비교합니다. 새로운 값이 이전 값과 다르면, **`film_text`** 테이블에서 해당 **`film_id`**를 찾아 새로운 값을 **`UPDATE`**합니다.

```sql
DELIMITER ;;
CREATE TRIGGER `upd_film` AFTER UPDATE ON `film` FOR EACH ROW BEGIN
    IF (old.title != new.title) OR (old.description != new.description) OR (old.film_id != new.film_id)
    THEN
        UPDATE film_text
            SET title=new.title,
                description=new.description,
                film_id=new.film_id
        WHERE film_id=old.film_id;
    END IF;
  END;;
DELIMITER ;
```

## `del_film`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

위 SQL 문장은 **`film`** 테이블에서 데이터가 삭제될 때마다 실행됩니다.

```sql
DELIMITER ;;
CREATE TRIGGER `del_film` AFTER DELETE ON `film` FOR EACH ROW BEGIN
    DELETE FROM film_text WHERE film_id = old.film_id;
  END;;
DELIMITER ;
```

---

# `inventory`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 DVD 대여점에서 DVD 재고를 추적하는 데 사용됩니다.

```sql
CREATE TABLE inventory (
  inventory_id MEDIUMINT UNSIGNED NOT NULL AUTO_INCREMENT,
  film_id SMALLINT UNSIGNED NOT NULL,
  store_id TINYINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (inventory_id),
  KEY idx_fk_film_id (film_id),
  KEY idx_store_id_film_id (store_id,film_id),
  CONSTRAINT fk_inventory_store FOREIGN KEY (store_id) REFERENCES store (store_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_inventory_film FOREIGN KEY (film_id) REFERENCES film (film_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`inventory_id`**: 재고 ID입니다.
- **`film_id`**: 재고 대상인 DVD 영화의 ID입니다.
- **`store_id`**: 해당 재고가 속한 대여점의 ID입니다.
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# `language`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 언어 관련 정보를 저장하기 위해 만들어졌습니다.

```sql
CREATE TABLE language (
  language_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  name CHAR(20) NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- `language_id` : 언어 ID를 저장합니다.
- **`name`**: 언어 이름을 저장합니다.
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

---

# `payment`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 영화 대여 업무에서 고객이 대여한 영화에 대한 결제 정보를 저장하는 데 사용됩니다.

```sql
CREATE TABLE payment (
  payment_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
  customer_id SMALLINT UNSIGNED NOT NULL,
  staff_id TINYINT UNSIGNED NOT NULL,
  rental_id INT DEFAULT NULL,
  amount DECIMAL(5,2) NOT NULL,
  payment_date DATETIME NOT NULL,
  last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (payment_id),
  KEY idx_fk_staff_id (staff_id),
  KEY idx_fk_customer_id (customer_id),
  CONSTRAINT fk_payment_rental FOREIGN KEY (rental_id) REFERENCES rental (rental_id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_payment_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_payment_staff FOREIGN KEY (staff_id) REFERENCES staff (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- `payment_id` : 결제 ID를 나타내는 컬럼
- `customer_id`: 결제를 한 고객의 ID를 나타내는 컬럼
- `staff_id` : 결제를 처리한 직원의 ID를 나타내는 컬럼
- `rental_id` : 결제와 관련된 대여 ID를 나타내는 컬럼
- `amount`: 결제 금액을 나타내는 decimal(5,2) 컬럼
- `payment_date` : 결제 일시를 나타내는 컬럼
- **`last_update`**: 마지막으로 레코드가 업데이트된 시간 정보를 저장하는 컬럼

## `sales_by_store`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

각 매장의 총 매출을 나타내는 뷰입니다.

```sql
CREATE VIEW sales_by_store
AS
SELECT
CONCAT(c.city, _utf8mb4',', cy.country) AS store
, CONCAT(m.first_name, _utf8mb4' ', m.last_name) AS manager
, SUM(p.amount) AS total_sales
FROM payment AS p
INNER JOIN rental AS r ON p.rental_id = r.rental_id
INNER JOIN inventory AS i ON r.inventory_id = i.inventory_id
INNER JOIN store AS s ON i.store_id = s.store_id
INNER JOIN address AS a ON s.address_id = a.address_id
INNER JOIN city AS c ON a.city_id = c.city_id
INNER JOIN country AS cy ON c.country_id = cy.country_id
INNER JOIN staff AS m ON s.manager_staff_id = m.staff_id
GROUP BY s.store_id
ORDER BY cy.country, c.city;
```

## `sales_by_film_category`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 뷰는 영화 카테고리 별 매출 정보를 제공합니다.

```sql
CREATE VIEW sales_by_film_category
AS
SELECT
c.name AS category
, SUM(p.amount) AS total_sales
FROM payment AS p
INNER JOIN rental AS r ON p.rental_id = r.rental_id
INNER JOIN inventory AS i ON r.inventory_id = i.inventory_id
INNER JOIN film AS f ON i.film_id = f.film_id
INNER JOIN film_category AS fc ON f.film_id = fc.film_id
INNER JOIN category AS c ON fc.category_id = c.category_id
GROUP BY c.name
```

---

# `rental`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

대여 정보를 저장하기 위한 테이블입니다.

```sql
CREATE TABLE rental (
  rental_id INT NOT NULL AUTO_INCREMENT,
  rental_date DATETIME NOT NULL,
  inventory_id MEDIUMINT UNSIGNED NOT NULL,
  customer_id SMALLINT UNSIGNED NOT NULL,
  return_date DATETIME DEFAULT NULL,
  staff_id TINYINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (rental_id),
  UNIQUE KEY  (rental_date,inventory_id,customer_id),
  KEY idx_fk_inventory_id (inventory_id),
  KEY idx_fk_customer_id (customer_id),
  KEY idx_fk_staff_id (staff_id),
  CONSTRAINT fk_rental_staff FOREIGN KEY (staff_id) REFERENCES staff (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_rental_inventory FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- **`rental_id`**: 대여 ID를 나타내는 컬럼
- **`rental_date`**: 대여 시작일을 나타내는 컬럼
- **`inventory_id`**: 대여한 비디오 테이프의 인벤토리 ID를 나타내는 컬럼
- **`customer_id`**: 대여한 고객의 ID를 나타내는 컬럼
- **`return_date`**: 반납일을 나타내는 컬럼
- **`staff_id`**: 대여를 처리한 직원의 ID를 나타내는 컬럼
- **`last_update`**: 마지막으로 테이블이 업데이트된 날짜와 시간을 나타내는 컬럼

---

# `staff`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

테이블 **`staff`**는 스태프 정보를 저장하는 테이블입니다.

```sql
CREATE TABLE staff (
  staff_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(45) NOT NULL,
  last_name VARCHAR(45) NOT NULL,
  address_id SMALLINT UNSIGNED NOT NULL,
  picture BLOB DEFAULT NULL,
  email VARCHAR(50) DEFAULT NULL,
  store_id TINYINT UNSIGNED NOT NULL,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  username VARCHAR(16) NOT NULL,
  password VARCHAR(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (staff_id),
  KEY idx_fk_store_id (store_id),
  KEY idx_fk_address_id (address_id),
  CONSTRAINT fk_staff_store FOREIGN KEY (store_id) REFERENCES store (store_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_staff_address FOREIGN KEY (address_id) REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- `staff_id` : 스태프의 ID를 저장하는 컬럼
- **`first_name`**: 스태프의 이름 중 이름 부분을 저장하는 컬럼
- **`last_name`**: 스태프의 이름 중 성 부분을 저장하는 컬럼
- **`address_id`**: 스태프의 주소를 저장하는 컬럼
- **`picture`**: 스태프 사진을 저장하는 컬럼
- **`email`**: 스태프 이메일 주소를 저장하는 컬럼
- **`store_id`**: 해당 스태프가 근무하는 매장의 ID를 저장하는 컬럼
- **`active`**: 스태프의 활성화 여부를 저장하는 컬럼
- **`username`**: 스태프의 로그인 아이디를 저장하는 컬럼
- **`password`**: 스태프의 로그인 비밀번호를 저장하는 컬럼
- **`last_update`**: 마지막으로 테이블이 업데이트된 날짜와 시간을 나타내는 컬럼

## `staff_list`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

스태프 정보와 해당 주소 정보, 그리고 해당 도시와 국가 정보를 함께 조회

```sql
CREATE VIEW staff_list
AS
SELECT s.staff_id AS ID, CONCAT(s.first_name, _utf8mb4' ', s.last_name) AS name, a.address AS address, a.postal_code AS `zip code`, a.phone AS phone,
	city.city AS city, country.country AS country, s.store_id AS SID
FROM staff AS s JOIN address AS a ON s.address_id = a.address_id JOIN city ON a.city_id = city.city_id
	JOIN country ON city.country_id = country.country_id;
```

---

# `store`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 테이블은 영화 대여점의 지점(store) 정보를 담고 있습니다.

```sql
CREATE TABLE store (
  store_id TINYINT UNSIGNED NOT NULL AUTO_INCREMENT,
  manager_staff_id TINYINT UNSIGNED NOT NULL,
  address_id SMALLINT UNSIGNED NOT NULL,
  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY  (store_id),
  UNIQUE KEY idx_unique_manager (manager_staff_id),
  KEY idx_fk_address_id (address_id),
  CONSTRAINT fk_store_staff FOREIGN KEY (manager_staff_id) REFERENCES staff (staff_id) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT fk_store_address FOREIGN KEY (address_id) REFERENCES address (address_id) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

- `store_id` : 가게 ID를 저장하는 컬럼
- `manager_staff_id`: 매니저 스태프 ID를 저장하는 컬럼
- `address_id`: 주소 ID를 저장하는 컬럼
- **`last_update`**: 마지막으로 테이블이 업데이트된 날짜와 시간을 나타내는 컬럼

---

# Procedure

# `rewards_report`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

```sql
DELIMITER //

CREATE PROCEDURE rewards_report (
    IN min_monthly_purchases TINYINT UNSIGNED
    , IN min_dollar_amount_purchased DECIMAL(10,2)
    , OUT count_rewardees INT
)
LANGUAGE SQL
NOT DETERMINISTIC
READS SQL DATA
SQL SECURITY DEFINER
COMMENT 'Provides a customizable report on best customers'
proc: BEGIN

    DECLARE last_month_start DATE;
    DECLARE last_month_end DATE;

    /* Some sanity checks... */
    IF min_monthly_purchases = 0 THEN
        SELECT 'Minimum monthly purchases parameter must be > 0';
        LEAVE proc;
    END IF;
    IF min_dollar_amount_purchased = 0.00 THEN
        SELECT 'Minimum monthly dollar amount purchased parameter must be > $0.00';
        LEAVE proc;
    END IF;

    /* Determine start and end time periods */
    SET last_month_start = DATE_SUB(CURRENT_DATE(), INTERVAL 1 MONTH);
    SET last_month_start = STR_TO_DATE(CONCAT(YEAR(last_month_start),'-',MONTH(last_month_start),'-01'),'%Y-%m-%d');
    SET last_month_end = LAST_DAY(last_month_start);

    /*
        Create a temporary storage area for
        Customer IDs.
    */
    CREATE TEMPORARY TABLE tmpCustomer (customer_id SMALLINT UNSIGNED NOT NULL PRIMARY KEY);

    /*
        Find all customers meeting the
        monthly purchase requirements
    */
    INSERT INTO tmpCustomer (customer_id)
    SELECT p.customer_id
    FROM payment AS p
    WHERE DATE(p.payment_date) BETWEEN last_month_start AND last_month_end
    GROUP BY customer_id
    HAVING SUM(p.amount) > min_dollar_amount_purchased
    AND COUNT(customer_id) > min_monthly_purchases;

    /* Populate OUT parameter with count of found customers */
    SELECT COUNT(*) FROM tmpCustomer INTO count_rewardees;

    /*
        Output ALL customer information of matching rewardees.
        Customize output as needed.
    */
    SELECT c.*
    FROM tmpCustomer AS t
    INNER JOIN customer AS c ON t.customer_id = c.customer_id;

    /* Clean up */
    DROP TABLE tmpCustomer;
END //

DELIMITER ;
```

### 입력 변수

1. **`min_monthly_purchases` :** 최소 월 구매 횟수
2. **`min_dollar_amount_purchased` :** 최소 월 구매 금액

### 출력 변수

1. **`count_rewardees`**

### 동작 내용

프로시저 내에서는 일련의 조건문을 사용하여 최소 월별 구매 횟수 및 최소 월별 구매 금액 등의 유효성을 검사합니다. 그런 다음, 지난 달의 시작 날짜 및 종료 날짜를 계산하여 해당 기간 동안 구매한 고객을 식별하는 임시 테이블을 만듭니다. 임시 테이블에서 보상 대상 고객 수를 세는 SELECT 문이 실행되고, 임시 테이블과 고객 테이블의 JOIN을 사용하여 보상 대상 고객의 정보를 선택적으로 반환합니다. 마지막으로 임시 테이블을 삭제하여 데이터베이스에 남아 있지 않도록 합니다.

1. 마지막 달의 시작 및 끝일을 찾는다.
2. 임시 테이블 **`tmpCustomer`**를 만든다.
3. **`payment`** 테이블에서 마지막 달의 날짜 범위 내에서 고객 ID별 결제 금액의 총 합이 **`min_dollar_amount_purchased`**보다 크고 결제 횟수가 **`min_monthly_purchases`**보다 큰 고객 ID를 선택하여 임시 테이블 **`tmpCustomer`**에 삽입한다.
4. **`tmpCustomer`** 테이블에 삽입된 고객의 수를 **`count_rewardees`** 출력 매개 변수에 할당한다.
5. **`tmpCustomer`**와 **`customer`** 테이블을 조인하여 일치하는 모든 고객 정보를 출력한다.
6. **`tmpCustomer`** 테이블을 삭제한다.

# `film_in_stock`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

해당하는 필름과 상점에 대해 재고 수를 계산하여 출력하는 프로시저입니다.

```sql
DELIMITER $$

CREATE PROCEDURE film_in_stock(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
READS SQL DATA
BEGIN
     SELECT inventory_id
     FROM inventory
     WHERE film_id = p_film_id
     AND store_id = p_store_id
     AND inventory_in_stock(inventory_id);

     SELECT COUNT(*)
     FROM inventory
     WHERE film_id = p_film_id
     AND store_id = p_store_id
     AND inventory_in_stock(inventory_id)
     INTO p_film_count;
END $$

DELIMITER ;
```

### 입력 변수

- `p_film_id` : 필름 id
- `p_store_id` : 상점 id

### 출력 변수

- `p_film_count` : 필름의 재고 수

### 동작 내용

1. **`inventory`** 테이블에서 p_film_id와 p_store_id와 일치하는 inventory_id를 선택한다.
2. **`inventory`** 테이블에서 p_film_id와 p_store_id와 일치하는 inventory_id를 선택하고, 해당 재고가 있는 경우 COUNT(*)로 행 수를 p_film_count에 할당한다.

# `film_not_in_stock`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

해당하는 필름과 상점에 대해 재고가 없는 수를 계산하여 출력하는 프로시저입니다.

```sql
DELIMITER $$

CREATE PROCEDURE film_not_in_stock(IN p_film_id INT, IN p_store_id INT, OUT p_film_count INT)
READS SQL DATA
BEGIN
     SELECT inventory_id
     FROM inventory
     WHERE film_id = p_film_id
     AND store_id = p_store_id
     AND NOT inventory_in_stock(inventory_id);

     SELECT COUNT(*)
     FROM inventory
     WHERE film_id = p_film_id
     AND store_id = p_store_id
     AND NOT inventory_in_stock(inventory_id)
     INTO p_film_count;
END $$

DELIMITER ;
```

### 입력 변수

- `p_film_id` :  영화 ID
- `p_store_id` : 상점 ID

### 출력 변수

- `p_film_count` :  재고가 없는 영화 수

### 동작 내용

1. 영화 ID와 상점 ID가 주어지면 해당 영화의 재고가 없는 인벤토리 ID를 찾습니다.
2. 해당 영화의 재고가 없는 인벤토리 수를 계산합니다.
3. p_film_count에 계산된 수를 할당합니다.

---

# Function

# `get_customer_balance`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 함수의 목적은 특정 고객의 잔액을 계산하는 것입니다.

```sql
DELIMITER $$

CREATE FUNCTION get_customer_balance(p_customer_id INT, p_effective_date DATETIME) RETURNS DECIMAL(5,2)
    DETERMINISTIC
    READS SQL DATA
BEGIN

       #OK, WE NEED TO CALCULATE THE CURRENT BALANCE GIVEN A CUSTOMER_ID AND A DATE
       #THAT WE WANT THE BALANCE TO BE EFFECTIVE FOR. THE BALANCE IS:
       #   1) RENTAL FEES FOR ALL PREVIOUS RENTALS
       #   2) ONE DOLLAR FOR EVERY DAY THE PREVIOUS RENTALS ARE OVERDUE
       #   3) IF A FILM IS MORE THAN RENTAL_DURATION * 2 OVERDUE, CHARGE THE REPLACEMENT_COST
       #   4) SUBTRACT ALL PAYMENTS MADE BEFORE THE DATE SPECIFIED

  DECLARE v_rentfees DECIMAL(5,2); #FEES PAID TO RENT THE VIDEOS INITIALLY
  DECLARE v_overfees INTEGER;      #LATE FEES FOR PRIOR RENTALS
  DECLARE v_payments DECIMAL(5,2); #SUM OF PAYMENTS MADE PREVIOUSLY

  SELECT IFNULL(SUM(film.rental_rate),0) INTO v_rentfees
    FROM film, inventory, rental
    WHERE film.film_id = inventory.film_id
      AND inventory.inventory_id = rental.inventory_id
      AND rental.rental_date <= p_effective_date
      AND rental.customer_id = p_customer_id;

  SELECT IFNULL(SUM(IF((TO_DAYS(rental.return_date) - TO_DAYS(rental.rental_date)) > film.rental_duration,
        ((TO_DAYS(rental.return_date) - TO_DAYS(rental.rental_date)) - film.rental_duration),0)),0) INTO v_overfees
    FROM rental, inventory, film
    WHERE film.film_id = inventory.film_id
      AND inventory.inventory_id = rental.inventory_id
      AND rental.rental_date <= p_effective_date
      AND rental.customer_id = p_customer_id;

  SELECT IFNULL(SUM(payment.amount),0) INTO v_payments
    FROM payment

    WHERE payment.payment_date <= p_effective_date
    AND payment.customer_id = p_customer_id;

  RETURN v_rentfees + v_overfees - v_payments;
END $$

DELIMITER ;
```

### 입력 변수

1. **`p_customer_id`**: 계산할 고객의 ID입니다.
2. **`p_effective_date`**: 계산할 년/월/일입니다. 이 날짜까지의 대여 및 지불 내역을 기반으로 계산합니다.

### 동작 내용

1. 주어진 날짜에 대해 이전 렌탈에 대한 렌탈료를 계산합니다. 이전 렌탈은 대여일자가 주어진 날짜보다 이전인 것으로 가정됩니다.
2. 이전 대여물품에 대한 연체료를 계산합니다. 대여 일수가 대여 기간보다 큰 경우, 연체 일수에 대해 일당 1달러의 연체료가 부과됩니다.
3. 이전에 결제된 금액을 고객의 잔액에서 차감합니다.
4. 1,2,3번의 계산을 통해 고객의 잔액을 계산하여 반환합니다.

# `inventory_held_by_customer`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

이 함수는 주어진 인벤토리 ID를 가진 대여 중인 비디오의 고객 ID를 반환합니다.

```sql
DELIMITER $$

CREATE FUNCTION inventory_held_by_customer(p_inventory_id INT) RETURNS INT
READS SQL DATA
BEGIN
  DECLARE v_customer_id INT;
  DECLARE EXIT HANDLER FOR NOT FOUND RETURN NULL;

  SELECT customer_id INTO v_customer_id
  FROM rental
  WHERE return_date IS NULL
  AND inventory_id = p_inventory_id;

  RETURN v_customer_id;
END $$

DELIMITER ;
```

### 입력 변수

1. **`v_customer_id`** : 이 변수는 대여 중인 고객의 ID를 저장하기 위한 변수입니다.

### 출력 변수

1. **`v_customer_id` :** 

### 동작 내용

1. **`v_customer_id`**를 선언합니다.
2. **`NOT FOUND`** 예외를 처리하기 위한 **`EXIT HANDLER`**를 설정합니다.
3. **`rental`** 테이블에서 **`return_date`** 값이 **`NULL`**이고, **`inventory_id`** 값이 **`p_inventory_id`**와 일치하는 레코드의 **`customer_id`** 값을 **`v_customer_id`**에 할당합니다.
4. **`v_customer_id`** 값을 반환합니다. **`NOT FOUND`** 예외가 발생한 경우에는 **`NULL`**을 반환합니다.

# `inventory_in_stock`

[[테이블 목차 보기]](https://www.notion.so/MySQL-sakila-93b15031035d4aa8b708a5af01291f1a)

입력된 재고 ID에 대한 대여 상태를 확인하여, 해당 재고가 현재 대여 가능한 상태인지를 반환하는 함수입니다.

```sql
DELIMITER $$

CREATE FUNCTION inventory_in_stock(p_inventory_id INT) RETURNS BOOLEAN
READS SQL DATA
BEGIN
    DECLARE v_rentals INT;
    DECLARE v_out     INT;

    #AN ITEM IS IN-STOCK IF THERE ARE EITHER NO ROWS IN THE rental TABLE
    #FOR THE ITEM OR ALL ROWS HAVE return_date POPULATED

    SELECT COUNT(*) INTO v_rentals
    FROM rental
    WHERE inventory_id = p_inventory_id;

    IF v_rentals = 0 THEN
      RETURN TRUE;
    END IF;

    SELECT COUNT(rental_id) INTO v_out
    FROM inventory LEFT JOIN rental USING(inventory_id)
    WHERE inventory.inventory_id = p_inventory_id
    AND rental.return_date IS NULL;

    IF v_out > 0 THEN
      RETURN FALSE;
    ELSE
      RETURN TRUE;
    END IF;
END $$

DELIMITER ;
```

### 입력 변수

1. **`p_inventory_id`**: 재고 아이디 

### 출력 변수

1. `BOOLEAN`

### 동작 내용

1. 인벤토리 ID를 받습니다.
2. 인벤토리 ID에 해당하는 대여 기록 수를 조회합니다.
3. 대여 기록이 없는 경우, 인벤토리가 재고에 있다고 판단하고 TRUE 값을 반환합니다.
4. 대여 기록이 있는 경우, 현재 인벤토리가 대여 중인지를 조회합니다.
5. 대여 중이 아닌 경우, 인벤토리가 재고에 있다고 판단하고 TRUE 값을 반환합니다.
6. 대여 중인 경우, 인벤토리가 재고에 없다고 판단하고 FALSE 값을 반환합니다.