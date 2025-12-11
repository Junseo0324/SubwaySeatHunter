# 프로젝트 할 일 목록 (Test-Driven Data Layer)

## 0. 프로젝트 초기 설정 및 테스트 환경 구축
- [x] **기본 프로젝트 설정**
    - [x] Clean Architecture 패키지 구조 (Presentation, Domain, Data) 설정
    - [x] Hilt (Dependency Injection) 설정
    - [x] Kotlin Coroutines & Flow 설정
- [x] **테스트 의존성 추가**
    - [x] JUnit4, MockK (Mocking)
    - [x] com.google.truth (Assertions)
    - [x] kotlinx-coroutines-test (TestDispatcher, runTest)
    - [x] app.cash.turbine (Flow Testing)
    - [x] okhttp3:mockwebserver (Network Testing)

## 1. 정적 데이터 레이어 (CSV -> Room) 구현
> **목표**: 오프라인 통계 및 역 정보를 CSV에서 파싱하여 로컬 DB에 저장. 파싱 로직과 DB 저장 로직의 단위 테스트 보장.

### 1.1 CSV 파싱 로직 (Test First)
- [x] **[Test]** CSV 파서 유닛 테스트 작성
    - [x] 샘플 CSV 데이터를 주입받아 올바른 리스트 객체로 변환하는지 검증
    - [x] 잘못된 형식의 CSV 처리 테스트
- [x] `AssetDataSource` 및 `CsvParser` 구현
- [x] DTO 정의 (`StationCsvDto`, `CongestionCsvDto`)

### 1.2 로컬 데이터베이스 (Room)
- [x] Entities 정의 (`StationEntity`, `CongestionEntity`)
- [x] **[Test]** DAO 유닛 테스트 작성 (In-Memory Database)
    - [x] `insert` 및 `get` 동작 검증
    - [x] 대량 데이터 삽입 시 성능/정합성 확인
- [x] `AppDatabase` 및 DAO 구현 (`StationDao`, `CongestionDao`)

### 1.3 Repository (CSV -> DB 적재)
- [x] Domain Model 및 Repository Interface 정의
- [x] **[Test]** `StationRepository` 초기 데이터 로드 테스트
    - [x] "DB가 비어있을 때 CSV 파싱 후 저장하는가?" 시나리오 검증
    - [x] Repository가 Domain Model로 올바르게 변환하여 반환하는지 검증
- [x] `StationRepositoryImpl` 구현
- [x] `RoomCallback` 또는 `Worker`를 통한 초기 데이터 시딩(Seeding) 로직

## 2. 실시간 데이터 레이어 (Network) 구현
> **목표**: 서울시 API 연동. MockWebServer를 사용하여 네트워크 응답 케이스(성공, 실패, 이상한 데이터)를 확실하게 테스트.

### 2.1 API 모델 및 파싱
- [x] API JSON 응답 스펙 분석 및 JSON 샘플 파일 확보
- [x] **[Test]** DTO 파싱 테스트
    - [x] Moshi/Gson 어댑터 동작 확인
    - [x] Null Field 처리 및 Default Value 확인
- [x] `SubwayArrivalResponse` DTO 구현

### 2.2 Retrofit Service (MockWebServer)
- [ ] **[Test]** Retrofit Service 유닛 테스트
    - [ ] `MockWebServer`로 200 OK 응답 시 호출 성공 검증
    - [ ] 40x, 50x 에러 응답 시 예외 처리 검증
- [ ] `SubwayApiService` 인터페이스 및 Retrofit Client 구현 (`OkHttp` Interceptor 설정 포함)

### 2.3 RemoteDataSource & Repository
- [ ] **[Test]** `RealtimeRepository` 테스트
    - [ ] 데이터 소스에서 데이터를 가져와 Domain Model로 매핑하는 로직 검증
    - [ ] `Result<T>` (Success/Failure) 래퍼 클래스 처리 로직 검증
- [ ] `RealtimeSubwayRepositoryImpl` 구현

## 3. 도메인 레이어 (Pure Kotlin)
> **목표**: 비즈니스 로직(예: 칸 추천 알고리즘)을 순수 코틀린 코드로 작성하고 테스트.

- [ ] **UseCases** 정의
- [ ] **[Test]** `GetNearestStationUseCase` 테스트 (위치 알고리즘)
- [ ] **[Test]** `GetCongestionForecastUseCase` 테스트 (통계 + 실시간 결합 로직)
- [ ] UseCase 구현

## 4. UI 레이어 (Presentation)
> **목표**: 실제 UI 구현 전 ViewModel 로직 테스트.

- [ ] **[Test]** MainViewModel 테스트
    - [ ] UseCase 결과를 `StateFlow` 상태로 잘 변환하는지 검증 (Loading -> Success/Error)
- [ ] UI 컴포넌트 구현 (Compose/XML)
    - [ ] 메인 화면
    - [ ] 상세 화면

## 5. 통합 및 E2E 테스트
- [ ] Hilt 의존성 주입 확인
- [ ] 핵심 사용자 시나리오(Happy Path) UI 테스트
