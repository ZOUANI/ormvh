import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { OrmvahTestModule } from '../../../test.module';
import { ExpeditorTypeComponent } from 'app/entities/expeditor-type/expeditor-type.component';
import { ExpeditorTypeService } from 'app/entities/expeditor-type/expeditor-type.service';
import { ExpeditorType } from 'app/shared/model/expeditor-type.model';

describe('Component Tests', () => {
  describe('ExpeditorType Management Component', () => {
    let comp: ExpeditorTypeComponent;
    let fixture: ComponentFixture<ExpeditorTypeComponent>;
    let service: ExpeditorTypeService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [OrmvahTestModule],
        declarations: [ExpeditorTypeComponent],
      })
        .overrideTemplate(ExpeditorTypeComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ExpeditorTypeComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ExpeditorTypeService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ExpeditorType(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.expeditorTypes && comp.expeditorTypes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
