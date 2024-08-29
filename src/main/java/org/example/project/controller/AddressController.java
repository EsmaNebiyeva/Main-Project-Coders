package org.example.project.controller;

//
//@RestController
//@RequestMapping("/api/address")
//@RequiredArgsConstructor
//public class AddressController {
//    @Autowired
//    private final AddressService addressService;
//    @GetMapping("/get")
//    public ResponseEntity<Address> getAddress(@RequestBody Address address) {
//      try{
//          addressService.saveAddress(address);
//          return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
//      }
//      catch(Exception e){
//          return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//      }
//
//    }
//    @DeleteMapping("delete")
//    public ResponseEntity<Address> deleteAddress(@RequestBody Address address) {
//        try {
//            addressService.deleteAddress(address);
//        }
//        catch(Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>(address, HttpStatus.ACCEPTED);
//    }
//}
