import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IVoie } from 'app/shared/model/voie.model';
import { AccountService } from 'app/core';
import { VoieService } from './voie.service';

@Component({
  selector: 'jhi-voie',
  templateUrl: './voie.component.html'
})
export class VoieComponent implements OnInit, OnDestroy {
  voies: IVoie[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected voieService: VoieService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.voieService
      .query()
      .pipe(
        filter((res: HttpResponse<IVoie[]>) => res.ok),
        map((res: HttpResponse<IVoie[]>) => res.body)
      )
      .subscribe(
        (res: IVoie[]) => {
          this.voies = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInVoies();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVoie) {
    return item.id;
  }

  registerChangeInVoies() {
    this.eventSubscriber = this.eventManager.subscribe('voieListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
