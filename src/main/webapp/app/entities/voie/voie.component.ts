import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVoie } from 'app/shared/model/voie.model';
import { VoieService } from './voie.service';
import { VoieDeleteDialogComponent } from './voie-delete-dialog.component';

@Component({
  selector: 'jhi-voie',
  templateUrl: './voie.component.html',
})
export class VoieComponent implements OnInit, OnDestroy {
  voies?: IVoie[];
  eventSubscriber?: Subscription;

  constructor(protected voieService: VoieService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.voieService.query().subscribe((res: HttpResponse<IVoie[]>) => (this.voies = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInVoies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IVoie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInVoies(): void {
    this.eventSubscriber = this.eventManager.subscribe('voieListModification', () => this.loadAll());
  }

  delete(voie: IVoie): void {
    const modalRef = this.modalService.open(VoieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.voie = voie;
  }
}
