import { Component, OnInit } from "@angular/core";
import {Employee} from "../../model/employee";
import {EmployeeService} from "../../model/employee.service";
import {Router} from "@angular/router";
import {CompanyService} from "../../model/company.service";
import {Observable} from "rxjs";
import {Company} from "../../model/company";

@Component({
  selector: 'app-create-employee',
  templateUrl: './create-employee.component.html',
  styleUrls: ['./create-employee.component.css']
})
export class CreateEmployeeComponent implements OnInit {
  employee: Employee = new Employee();
  companies: Observable<Company[]>;
  submitted = false;

  constructor(private employeeService: EmployeeService, private router: Router,
              private companyService: CompanyService) { }

  ngOnInit(): void {
    this.loadListCompany();
  }

  newEmployee(): void {
    this.submitted = false;
    this.employee = new Employee();
  }

  save() {
    this.employeeService.createEmployee(this.employee)
      .subscribe(data => console.log(data), error => console.log(error));
    this.employee = new Employee();
    this.gotoList();
  }

  loadListCompany(){
    this.companyService.getCompaniesList().subscribe(data=>{
      this.companies = data;
    },error => console.log(error));
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/employees']);
  }

}
