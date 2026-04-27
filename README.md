# CSE215Project


Suggested improvements:

ID auto-increment via a counter file — right now counters reset if you load data on a fresh run without re-deriving from loaded IDs (already handled, but saving the counter to file is cleaner).

Enum for departments — avoids typos like "CS" vs "Computer Science" across students and teachers.

Input validation layer — email format check, GPA bounds, duplicate name warnings, all in one Validator utility class.

Grade enum (A, B+, etc.) instead of raw doubles — maps cleaner to GPA points and is more realistic.

Serialize with ObjectOutputStream — replace the manual CSV .txt format with binary .dat serialization (just implement Serializable on each model class). Much less fragile.

Unit tests — a TestRunner.java that exercises add/enroll/GPA/file roundtrip without needing the menu, great for verifying correctness.
