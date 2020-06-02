import { Component, OnInit } from '@angular/core';
import {Company} from "../../model/company";
import {CompanyService} from "../../model/company.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-create-company',
  templateUrl: './create-company.component.html',
  styleUrls: ['./create-company.component.css']
})
export class CreateCompanyComponent implements OnInit {
  submitted: boolean;
  company: Company;

  constructor(private companyService: CompanyService, private router: Router) { }

  ngOnInit(): void {
    this.company = new Company();
  }

  save() {
    this.companyService.createCompany(this.company)
      .subscribe(data => console.log(data), error => console.log(error));
    this.company = new Company();
    this.gotoList();
  }

  onSubmit() {
    this.submitted = true;
    this.save();
  }

  gotoList() {
    this.router.navigate(['/companies']);
  }
}
