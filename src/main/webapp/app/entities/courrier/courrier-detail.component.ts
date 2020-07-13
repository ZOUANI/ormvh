import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICourrier } from 'app/shared/model/courrier.model';

@Component({
  selector: 'jhi-courrier-detail',
  templateUrl: './courrier-detail.component.html'
})
export class CourrierDetailComponent implements OnInit {
  courrier: ICourrier;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ courrier }) => {
      this.courrier = courrier;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
