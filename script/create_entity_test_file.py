files = [
    "Actor",
    "Address",
    "City",
    "Country",
    "Category",
    "FilmActor",
    "FilmCategory",
    "Film",
    "FilmText",
    "Language",
    "Inventory",
    "Payment",
    "Rental",
    "Store",
    "Customer",
    "Staff"
]

for file in files:
    with open(f'{file}Test.java', 'w') as f:
        f.write("\n")
        f.write(f'public class {file}' + 'Test {\n\n')
        
        f.write(f"public static {file} build{file}() " + "{}\n")

        f.write('}')