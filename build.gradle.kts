import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// https://docs.spring.io/spring-boot/docs/3.0.x/gradle-plugin/reference/htmlsingle/
plugins {
	id("org.springframework.boot") version "3.0.1"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	// https://github.com/etiennestuder/gradle-jooq-plugin#compatibility
	id("nu.studer.jooq") version "8.1"
	id("org.openapi.generator") version "6.2.1"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

// https://docs.spring.io/spring-boot/docs/3.0.x/reference/html/dependency-versions.html#appendix.dependency-versions
dependencies {
	implementation("org.springframework.boot:spring-boot-starter-jooq")
	// for "gradle-jooq-plugin" Minimum jOOQ 3.16+
	// https://github.com/etiennestuder/gradle-jooq-plugin#compatibility
	implementation("org.jooq:jooq")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.flywaydb:flyway-core")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.13.2")

	jooqGenerator("org.postgresql:postgresql")
	// https://github.com/etiennestuder/gradle-jooq-plugin/issues/207
	jooqGenerator("jakarta.xml.bind:jakarta.xml.bind-api:3.0.1")
}

jooq {
	version.set(dependencyManagement.importedProperties["jooq.version"])
	edition.set(nu.studer.gradle.jooq.JooqEdition.OSS)

	configurations {
		create("main") {
			// ref https://github.com/etiennestuder/gradle-jooq-plugin#generating-the-jooq-sources
			// ref https://www.greptips.com/posts/1350/#jooq-configurations
            generateSchemaSourceOnCompilation.set(false)
			jooqConfiguration.apply {
				jdbc.apply {
					driver = "org.postgresql.Driver"
					// TODO: 環境変数から読み取るようにし、direnv 等で設定する
					// url = System.getenv("POSTGRES_URL")
					// user = System.getenv("POSTGRES_USER")
					// password = System.getenv("POSTGRES_PASSWORD")
					url = "jdbc:postgresql://localhost:5432/household-expenses"
					user = "postgres"
					password = "password"
				}
				generator.apply {
					name = "org.jooq.codegen.KotlinGenerator"
					database.apply {
						name = "org.jooq.meta.postgres.PostgresDatabase"
						inputSchema = "public"
						excludes = "flyway_schema_history"
					}
					generate.apply {
						isDeprecated = false
						isTables = true
						// isRecords = true
						// isImmutablePojos = true
						// isFluentSetters = true
					}
					target.apply {
						packageName = "com.example.householdExpenses.jooq.codegen"
						// directory = "${buildDir}/generated/source/jooq/main"
						directory = "build/generated-src/jooq/"
					}
					strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
				}
			}
		}
	}
}

// test の task 実行時に generateJooq を実行しないための設定
// 実際は、 `generateSchemaSourceOnCompilation.set(false)` で無効にできたため不要
//tasks {
//    test {
//        findByName("generateJooq")?.enabled = false
//    }
//}

// https://openapi-generator.tech/docs/plugins/#gradle
openApiGenerate {
	generatorName.set("kotlin")
	inputSpec.set("$rootDir/docs/openapi/openapi.yaml")
	outputDir.set("$buildDir/openapi-generated")
	apiPackage.set("com.example.householdExpenses.openapi.api")
	invokerPackage.set("com.example.householdExpenses.openapi.invoker")
	modelPackage.set("com.example.householdExpenses.model")

	// https://openapi-generator.tech/docs/generators/kotlin#config-options
	configOptions.set(
		mapOf(
			"dateLibrary" to "java8",
			"serializationLibrary" to "jackson"
		),
	)
}

kotlin.sourceSets.main {
	// openapi-generator が build したコードを import できるようにする
	kotlin.srcDir("$buildDir/openapi-generated/src/main/kotlin/com/example/householdExpenses/model")

	// 以下のように指定すると、今回は使用しない api server のコードも含まれる
	// ex.) src/main/kotlin/com/example/householdExpenses/openapi/api/CategoriesApi.kt
	// kotlin.srcDir("$buildDir/openapi-generated/")
	// その中で使用している okhttp3 の依存が必要になるため dependencies に依存関係の記載が必要となる
	// compileOnly("com.squareup.okhttp3:okhttp:4.10.0")
}

tasks.withType<KotlinCompile> {

	// コンパイル前に実施
	// dependsOn("generateJooq")
	dependsOn("openApiGenerate")

	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
