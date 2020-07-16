import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { OrmvahTestModule } from '../../../test.module';
import { ExpeditorDetailComponent } from 'app/entities/expeditor/expeditor-detail.component';
import { Expeditor } from 'app/shared/model/expeditor.model';

describe('Component Tests', () => {
  describe('Expeditor Management Detail Component', () => {
    let comp: ExpeditorDetailComponent;
    let fixture: ComponentFixture<ExpeditorDetailComponent>;
    const route = ({ data: of({ expeditor: new Expeditor(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ExpeditorDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ExpeditorDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExpeditorDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load expeditor on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.expeditor).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
