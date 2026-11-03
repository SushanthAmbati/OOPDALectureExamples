# Design Principles: Favor Composition over Inheritance

**Big idea:** Inheritance (**is-a**) fixes behavior when the object is **created**. Composition (**has-a**) stores behavior in a field, so you can **mix, match and swap** it while the program runs. When a class varies in more than one independent way, give it fields for those behaviors rather than a subclass for every combination.

> This builds on [`../inheritance`](../inheritance/README.md), [`../interfaces`](../interfaces/README.md) and [`../lambdas`](../lambdas/README.md).

## The example

An online store's `Order` has a **shipping** cost that depends on the shipping option, and later a **payment** method. The two folders solve the same problem in two ways.

| Folder | Approach | Files | Start by running |
|--------|----------|-------|------------------|
| [`inheritance/`](inheritance/Driver.java) | `Order` **is-a** `StandardOrder` or `ExpressOrder` | [`Order`](inheritance/Order.java) (`abstract`), [`StandardOrder`](inheritance/StandardOrder.java), [`ExpressOrder`](inheritance/ExpressOrder.java), [`Driver`](inheritance/Driver.java) ▶ | `designprinciples.inheritance.Driver` |
| [`composition/`](composition/Driver.java) | `Order` **has-a** `ShippingStrategy` and **has-a** `PaymentMethod` | [`Order`](composition/Order.java), [`ShippingStrategy`](composition/ShippingStrategy.java), [`PaymentMethod`](composition/PaymentMethod.java), [`Driver`](composition/Driver.java) ▶ | `designprinciples.composition.Driver` |

Shipping costs used in both versions:

| Option | Cost | 2 kg book | 4 kg lamp | 10 kg laptop |
|--------|------|-----------|-----------|--------------|
| Standard | `5 + weight * 0.5` | 6.0 | 7.0 | 10.0 |
| Express | `15 + weight * 1.0` | 17.0 | 19.0 | 25.0 |
| Drone (composition only) | `50 + weight * 2.0` | 54.0 | 58.0 | 70.0 |

## Key concepts

### 1. With inheritance, the choice is locked in at `new`

```java
Order a = new StandardOrder("Book", 2.0);
// The customer changes their mind. An object can't change its class, so...
Order temp = new ExpressOrder("Book", 2.0);   // ...build a whole new object
a = temp;                                     // and copy the data over by hand
```

The shipping rule *is* the class, so changing it means throwing the object away.

### 2. With composition, the behavior is just a field

```java
Order a = new Order("Book", 2.0, standard, card);
a.setShipping(express);   // same object, new behavior
a.checkout();             // Charging card: $17.0
```

`Order` doesn't know *how* shipping is calculated. It only knows that its `shipping` field has a `cost(weight)` method. It's the same idea as `Calculator.operateBinary` in [`../lambdas`](../lambdas/README.md): `ShippingStrategy` and `PaymentMethod` are **functional interfaces**, so each behavior in `Driver` is a one-line lambda.

### 3. Inheritance multiplies, composition adds

Add a second thing that varies, the payment method, and count the classes you need:

| | Inheritance | Composition |
|---|-------------|-------------|
| 2 shipping × 1 payment | 2 subclasses | 2 lambdas |
| 3 shipping × 3 payment | **9** subclasses: `StandardCardOrder`, `StandardPayPalOrder`, `ExpressCryptoOrder`, … | **6** lambdas (3 + 3) |
| Add one new shipping option | 3 more subclasses, one per payment | 1 more lambda (`drone`) |
| Change behavior on an existing object |  create a new object | `setShipping(...)` / `setPayment(...)` |

### Is-a vs. has-a

| Question | If yes… |
|----------|---------|
| Is it **always** a kind of the other thing, with no exceptions? ("A `Manager` is an `Employee`.") | Inheritance is reasonable. |
| Is it a **part**, a **setting** or a **behavior** that could change or vary independently? ("An order *has* a shipping option.") | Use composition. |

*Favor* doesn't mean *never*. The [`../inheritance`](../inheritance/README.md) examples are good uses of `extends`. Composition is the better default when behavior varies.

## Gotchas

- **Composition with no interface gains nothing.** If `Order` held a concrete `ExpressShipping` field, you couldn't swap it. The field's type should be the **interface**.
- **`null` behavior crashes.** `new Order("Book", 2.0, null, card).checkout()` throws a `NullPointerException` when it calls `shipping.cost(...)`. Every has-a field needs a real object.
- **"Is-a" subclasses can break promises.** [`MisbehavingRedItem`](../inheritance/MisbehavingRedItem.java) shows a subclass undoing its superclass's rules. With composition, `Order` controls when and how its parts are used.

## Try it

1. Run both `Driver`s. In the inheritance version, why does `a` print `17.0` and not `6.0`?
2. In `composition/Driver.java`, the `drone` strategy is never used. Use it for a new order, then switch that order to `standard` before `checkout()`.
3. Add a `PaymentMethod` for gift cards that prints `"Gift card: $..."`. How many existing files did you have to change? Now imagine adding gift cards to the inheritance version.
4. Add **free shipping over 10 kg** as a fourth `ShippingStrategy` lambda (hint: the ternary operator `w > 10 ? 0 : ...`).
5. Write `ShippingStrategy` as a named class, `ExpressShipping implements ShippingStrategy`, and pass `new ExpressShipping()` to `setShipping`. Does `Order` notice the difference?
