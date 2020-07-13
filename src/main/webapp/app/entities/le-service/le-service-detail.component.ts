import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILeService } from 'app/shared/model/le-service.model';

@Component({
  selector: 'jhi-le-service-detail',
  templateUrl: './le-service-detail.component.html'
})
export class LeServiceDetailComponent implements OnInit {
  leService: ILeService;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ leService }) => {
      this.leService = leService;
    });
  }

  previousState() {
    window.history.back();
  }
}
