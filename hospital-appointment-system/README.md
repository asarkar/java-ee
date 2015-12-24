An example of the Richardson Maturity Model a.k.a. RMM and HATEOAS. Our problems domain is a hospital
  appointment system as follows:

  * A patient can query for open slots for a particular day. In response, they receive a list of
  slots that're open on that day. If no slots are available on the requested date, they get an appropriate response.
    - They can request information for a particular slot using the slot id.
    - They can browse the slots using the 'start' URI provided in the response and the 'next' or 'prev' links
  provided with an individual slot, if applicable (obviously the 1st slot has no 'prev' link, neither does the last
  slot has a 'next' link).
    - Given a slot URI, the slot can be reserved or if previously reserved, relinquished. This functionality is
   intended to be used by the appointment service and not directly by the patients.

  * Given a slot id, a patient can book an appointment or if previously booked, cancel an appointment.
  If a successful appointment is booked, the client receives a link to it along with a link to cancel the appointment.
  If for some reason the appointment booking failed, they get an appropriate response.

  Note: To book an appointment, a client must already be registered with the hospital.

  Most of the response is of type `application/hal+json`. HAL is built using HalBuilder.
  Links to the HAL spec, IANA-registered link relations and HalBuilder can be found in the 'References' section.

  References:

  [Richardson Maturity Model](http://martinfowler.com/articles/richardsonMaturityModel.html)

  [REST APIs must be hypertext-driven](http://roy.gbiv.com/untangled/2008/rest-apis-must-be-hypertext-driven)

  [HAL - Hypertext Application Language](http://stateless.co/hal_specification.html)

  [Link Relations](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

  [HalBuilder](https://github.com/HalBuilder)

  My design as as follows:

  `GET /slot` -> If slots are available, lists of all slots for current date.
    Returns HTTP status code 200. If no slots are available, returns HTTP status code 404.
  Date is according to the hospital.

  `GET /slot?date=20150101` -> Response similar to above, except for the date is provided by the patient.

  `GET /slot/1` -> Returns slot that has an id 1. Returns HTTP status code 200.
    If slot isn't found, returns HTTP status code 404.

  `PUT /slot/1?reserve=true` -> Reserves the slot that has an id 1. Returns same response as GET /slot/1.
    If slot found but not able to reserve, returns HTTP status code 409.

  `PUT /slot/1?reserve=false` -> Relinquishes the slot that has an id 1. Returns same response as GET /slot/1.
    If slot found but not able to relinquish, returns HTTP status code 409.

  For all of the below requests, it is checked that slot id and patient id are present. If not, the request is rejected
  with HTTP status code 400.
  The patient id is validated as well. If the patient is not registered, the request is rejected
  with HTTP status code 404.

  `GET /appointment/1?patientId=patientId` -> Returns appointment that has an id 1. Returns HTTP status code 200.
    If appointment isn't found, returns HTTP status code 404.

  `POST /appointment/1?patientId=patientId` -> Creates an appointment for a slot that has an id 1.
    Returns HTTP status code 201 and the URI of the newly created appointment.
    If failed to create appointment, returns HTTP status code 409.
    If slot isn't found, returns HTTP status code 404.

  `DELETE /appointment/1?patientId=patientId` -> Cancels an appointment that has an id 1.
    Returns HTTP status code 200 and the appointment that was deleted.
    If failed to cancel appointment, returns HTTP status code 409.
    If slot isn't found, HTTP status code 404.

  `GET /user;type=Doctor|Patient` -> Returns all registered users, doctors or patients, according to the type.
    Returns HTTP status code 200.
    If doctor isn't found, returns HTTP status code 404.

  `GET /user/1;type=Doctor|Patient` -> Returns a user that has an id 1, doctor or patient, according to the type.
    Returns HTTP status code 200.
    If doctor isn't found, returns HTTP status code 404.