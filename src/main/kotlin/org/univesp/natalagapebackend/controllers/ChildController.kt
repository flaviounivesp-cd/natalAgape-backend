package org.univesp.natalagapebackend.controllers


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.univesp.natalagapebackend.models.Child
import org.univesp.natalagapebackend.services.ChildService

@RestController
@RequestMapping("api/child")
class ChildController(val childService: ChildService) {

    @GetMapping
    fun listAll(): List<Child> = childService.listAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<Child> {
        return childService.findById(id).map { child ->
            ResponseEntity.ok(child)
        }.orElse(ResponseEntity.notFound().build())
    }

    @PostMapping
    fun save(@RequestBody child: Child): Child = childService.save(child)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody updatedChild: Child): ResponseEntity<Child> {
        return childService.findById(id).map { _ ->
            ResponseEntity.ok(childService.update(updatedChild))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        return childService.findById(id).map { child ->
            childService.deleteById(child.childId)
            ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }.orElse(ResponseEntity.notFound().build())
    }
}