import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { INatureCourrier } from 'app/shared/model/nature-courrier.model';
import { AccountService } from 'app/core';
import { NatureCourrierService } from './nature-courrier.service';

@Component({
  selector: 'jhi-nature-courrier',
  templateUrl: './nature-courrier.component.html'
})
export class NatureCourrierComponent implements OnInit, OnDestroy {
  natureCourriers: INatureCourrier[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected natureCourrierService: NatureCourrierService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.natureCourrierService
      .query()
      .pipe(
        filter((res: HttpResponse<INatureCourrier[]>) => res.ok),
        map((res: HttpResponse<INatureCourrier[]>) => res.body)
      )
      .subscribe(
        (res: INatureCourrier[]) => {
          this.natureCourriers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInNatureCourriers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: INatureCourrier) {
    return item.id;
  }

  registerChangeInNatureCourriers() {
    this.eventSubscriber = this.eventManager.subscribe('natureCourrierListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
