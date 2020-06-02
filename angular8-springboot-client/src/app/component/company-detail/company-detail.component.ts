import {Component, OnInit} from '@angular/core';
import {Company} from "../../model/company";
import {Router, ActivatedRoute} from '@angular/router';
import {CompanyService} from "../../model/company.service";

@Component({
  selector: 'app-company-detail',
  templateUrl: './company-detail.component.html',
  styleUrls: ['./company-detail.component.css']
})
export class CompanyDetailComponent implements OnInit {
  id: number;
  company: Company;

  constructor(private router: Router, private activatedRoute: ActivatedRoute,
              private companyService: CompanyService) {
  }

  ngOnInit(): void {
    this.company = new Company();
    this.id = this.activatedRoute.snapshot.params['id'];

    this.companyService.getCompany(this.id).subscribe(data =>{
      this.company = data;
    },error => console.log(error))
  }

  list(): void {
    this.router.navigate(['companies']);
  }
}
