### References

[Best Practices for Designing a Pragmatic RESTful API](http://www.vinaysahni.com/best-practices-for-a-pragmatic-restful-api)

[Thoughts on RESTful API Design](https://restful-api-design.readthedocs.org/en/latest)

[Design and Build RESTful API with Spring HATEOAS](https://www.jiwhiz.com/blogs/Design_and_Build_RESTful_API_with_Spring_HATEOAS)

[HAL JSON Specification](http://stateless.co/hal_specification.html)

[Link Relations](https://www.iana.org/assignments/link-relations/link-relations.xhtml)

### Headers

x-loginId

### URL Parameters

partnerId

##### GET (read)

/users/prefs?partnerId={partnerId}
200

```

{
    "_links": {
        "self": {
            "href": "/users/prefs/{name}/svc/{serviceId}?partnerId={partnerId}",
            "templated": true
        }
    }
}

```

/users/johndoe/prefs/favoriteStocks/svc/9
200 or 404

```

{
    "_links": {
        "self": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        },
        "edit": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        },
        "delete": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        }
    },
    "username": "johndoe",
    "preferences": [
        {
            "name": "favoriteStocks",
            "value": "APPL",
            "serviceId": "9"
        }
    ]
}

```

##### POST (create)

/users/johndoe/prefs/favoriteStocks/svc/9
value=AAPL

201 or 404 or 409

```

{
    "_links": {
        "self": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        },
        "edit": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        },
        "delete": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        }
    }
}

```

##### PUT (update)

/users/johndoe/prefs/favoriteStocks/svc/9
204 or 404


```

{
    "_links": {
        "self": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        },
        "edit": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        },
        "delete": {
            "href": "/users/johndoe/prefs/favoriteStocks/svc/9"
        }
    }
}

```

##### DELETE (delete)

/users/johndoe/prefs/favoriteStocks/svc/9
204 or 404

---

USERS
-----------------
id 	| username


USER_PREFS
-----------------
id  | name | svc_id | username


