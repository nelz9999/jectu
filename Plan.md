# Details #

Planned improvements:
  1. ~~Remove any dependency on TestNG or JUnit, just throw AssertionError~~ - Done in 0.1.0
  1. Superclasses - Use reflection to move up the class heirarchy
  1. Primitive arrays
  1. ~~Objects (Programmable?)~~ - Done in 0.2.0
  1. Reporting
  1. Site documentation
  1. Optional failure for bad hashCode implementation
  1. Transient fields
  1. Add a test against null object (sometimes the unequalObject should be null, maybe the exampleX object should automatically be null?  Or make multiple unequalObjects programmable, so they can test non-equal classes as well?  Also, test for equals against some final base class that doesn't override equals or hashCode (like StringBuffer?).)
  1. mvn assembly for source as well?