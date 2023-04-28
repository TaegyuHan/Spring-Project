files = [
    "ActorRepository",
    "CategoryRepository",
    "FilmActorRepository",
    "FilmCategoryRepository",
    "FilmRepository",
    "FilmTextRepository",
    "LanguageRepository",
    "InventoryRepository",
    "PaymentRepository",
    "RentalRepository",
    "StoreRepository",
    "CustomerRepository",
    "StaffRepository"
]


for file in files:
    with open(f'{file}Test.java', 'w') as f:
        f.write("import java.util.Optional;\n")
        f.write("import static org.assertj.core.api.Assertions.assertThat;\n")
        f.write("\n")
        f.write("@DataJpaTest\n")
        f.write("@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)\n")
        f.write(f'class {file}' + 'Test {\n\n')

        f.write("@Test\n")
        f.write("public void create() {}\n")

        f.write("@Test\n")
        f.write("public void read() {}\n")

        f.write("@Test\n")
        f.write("public void update() {}\n")

        f.write("@Test\n")
        f.write("public void delete() {}\n")

        f.write('}')